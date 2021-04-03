/**
 * Copyright 2021 NeutronStars
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.neutronstars.playermodel;

import fr.neutronstars.api.model.ModelException;
import fr.neutronstars.playermodel.event.RegisterPlayerEvent;
import fr.neutronstars.playermodel.event.UnregisterPlayerEvent;
import fr.neutronstars.playermodel.listener.PlayerHostListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.logging.Level;

/**
 * Permet de gérer les joueurs en jeu.
 *
 * @param <T> L'instance du plugin {@link Bukkit} ou est initialisé cette class.
 */
public class Players<T extends Plugin>
{
    /**
     * Créer une nouvelle instance de ce gestionnaire de joueur.
     * @param plugin Le plugin ou est initialisé cette class.
     * @param <T>    Le type de class {@link Plugin} ou est initialisé cette class.
     * @return Une nouvelle instance du gestionnaire de joueur.
     */
    public static <T extends Plugin> Players<T> create(T plugin)
    {
        return new Players<T>(plugin);
    }

    /**
     * Permet de stocker les joueurs en jeu.
     */
    protected final Map<UUID, Player> playerMap = new HashMap<>();

    /**
     * Permet de stocker les models à initialiser lorsque qu'un nouveau joueur se connecte.
     */
    protected final Set<Class<? extends PlayerModel>> models = new HashSet<>();

    /**
     * L'instance du plugin ou est initialisé cette class.
     */
    protected final T plugin;

    /**
     * Permet d'activer/désactivé l'enregistrement des nouveaux joueurs.
     */
    protected boolean registerPlayer = true;
    /**
     * Permet d'activer/désactivé la suppression des joueurs qui se déconnecte.
     */
    protected boolean unregisterPlayer = true;

    /**
     * Permet de créer une nouvelle instance du gestionnaire de joueur.
     * Le constructeur est en privé. Il faut utiliser la méthode static {@link Players#create(Plugin)} pour l'instancier.
     *
     * @param plugin le plugin ou est initialisé cette class.
     */
    private Players(T plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Permet de récupérer le plugin ou est instancié cette class.
     * @return récupère le plugin ou est instancié cette class.
     */
    public T getPlugin()
    {
        return this.plugin;
    }

    /**
     * Permet de récupérer un {@link Optional} de {@link Player}.
     *
     * Si le joueur n'existe pas et que l'option {@link Players#hasRegisterPlayer()} est activée alors un nouvelle instance
     * est faite et l'évènement {@link RegisterPlayerEvent} est lancé.
     *
     * Sinon l'{@link Optional} sera vide.
     *
     * @param player
     * @return
     */
    public Optional<Player> get(org.bukkit.entity.Player player)
    {
        if (this.registerPlayer && !playerMap.containsKey(player.getUniqueId())) {
            try {
                Player newPlayer = new Player(player.getUniqueId(), player.getName());
                for (Class<? extends PlayerModel> classModel : this.models) {
                    newPlayer.registerModel(classModel);
                }
                RegisterPlayerEvent event = new RegisterPlayerEvent(newPlayer);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled())
                {
                    event.getPlayer().ifPresent(p -> this.playerMap.put(p.getUniqueId(), p));
                }
            } catch (ModelException modelException) {
                this.plugin.getLogger().log(Level.SEVERE, modelException.getMessage(), modelException);
                return Optional.empty();
            }
        }
        return Optional.ofNullable(this.playerMap.get(player.getUniqueId()));
    }

    /**
     * Permet de supprimé une instance de {@link Player} dans ce gestionnaire si l'option {@link Players#hasUnregisterPlayer()}
     * est activé. Il appellera également l'évènement {@link UnregisterPlayerEvent}
     *
     * @param player Le joueur à supprimer du gestionnaire.
     * @return  L'instance de ce gestionnaire.
     */
    public Players<T> leave(Player player)
    {
        if (this.unregisterPlayer) {
            UnregisterPlayerEvent event = new UnregisterPlayerEvent(player);
            Bukkit.getPluginManager().callEvent(event);
            if (!event.isCancelled())
            {
                event.getPlayer().ifPresent(p -> this.playerMap.remove(p.getUniqueId()));
            }
        }
        return this;
    }

    /**
     * Permet de savoir si les nouveaux joueurs doivent-être enregistrés.
     * @return si les nouveaux joueurs doivent-être enregistrés.
     */
    public boolean hasRegisterPlayer()
    {
        return this.registerPlayer;
    }

    /**
     * Permet d'activer/désactiver l'enregistrement des nouveaux joueurs.
     * @param registerPlayer Si l'enregistrement des nouveaux joueurs doivent avoir lieu.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> setRegisterPlayer(boolean registerPlayer)
    {
        this.registerPlayer = registerPlayer;
        return this;
    }

    /**
     * Permet de savoir si les joueurs doivent-être supprimés lors de leur déconnexion.
     * @return si les joueurs doivent-être supprimés lors de leur déconnexion.
     */
    public boolean hasUnregisterPlayer()
    {
        return this.unregisterPlayer;
    }

    /**
     * Permet d'activer/désactiver la suppression des joueurs lors de leur déconnexion.
     * @param unregisterPlayer Si la suppression des joueurs doivent avoir lieu lors de leur déconnexion.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> setUnregisterPlayer(boolean unregisterPlayer)
    {
        this.unregisterPlayer = unregisterPlayer;
        return this;
    }

    /**
     * Permet d'activer/désactiver l'enregistrement des nouveaux joueurs et ou la suppression des joueurs lors de leur déconnexion.
     * @param registerPlayer Si l'enregistrement des nouveaux joueurs doivent avoir lieu.
     * @param unregisterPlayer Si la suppression des joueurs doivent avoir lieu lors de leur déconnexion.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> active(boolean registerPlayer, boolean unregisterPlayer)
    {
        return this.setRegisterPlayer(registerPlayer)
                   .setUnregisterPlayer(unregisterPlayer);
    }

    /**
     * Permet d'activer l'enregistrement des nouveaux joueurs et la suppression des joueurs lors de leur déconnexion.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> activeAll()
    {
        return this.active(true, true);
    }

    /**
     * Permet d'activer/désactiver l'enregistrement des nouveaux joueurs et ou la suppression des joueurs lors de leur déconnexion.
     * @param registerPlayer Si l'enregistrement des nouveaux joueurs ne doivent plus avoir lieu.
     * @param unregisterPlayer Si la suppression des joueurs ne doivent plus avoir lieu lors de leur déconnexion.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> disable(boolean registerPlayer, boolean unregisterPlayer)
    {
        return this.setRegisterPlayer(!registerPlayer)
                .setUnregisterPlayer(!unregisterPlayer);
    }

    /**
     * Permet de désactiver l'enregistrement des nouveaux joueurs et la suppression des joueurs lors de leur déconnexion.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> disableAll()
    {
        return this.disable(true, true);
    }

    /**
     * Permet de savoir si la class d'un {@link PlayerModel} est enregistré.
     * @param classModel la class du {@link PlayerModel} à verifier.
     * @return si la class du {@link PlayerModel} existe.
     */
    public boolean hasModel(Class<? extends PlayerModel> classModel)
    {
        return this.models.contains(classModel);
    }

    /**
     * Enregistre un nouveau {@link PlayerModel} à ce gestionnaire.
     * @param classModel le nouveau {@link PlayerModel} à enregistrer.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> registerModel(Class<? extends PlayerModel> classModel)
    {
        if (!this.hasModel(classModel)) {
            try {
                for (Player player : new ArrayList<>(this.playerMap.values())) {
                    player.registerModel(classModel);
                }
                this.models.add(classModel);
            } catch (ModelException exception) {
                this.plugin.getLogger().log(Level.SEVERE, exception.getMessage(), classModel);
            }
        }
        return this;
    }

    /**
     * Supprimer un {@link PlayerModel} de ce gestionnaire.
     * @param classModel le {@link PlayerModel} à supprimer.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> unRegisterModel(Class<? extends PlayerModel> classModel)
    {
        for (Player player : new ArrayList<>(this.playerMap.values())) {
            player.unregisterModel(classModel);
        }
        this.models.remove(classModel);
        return this;
    }

    /**
     * Permet de charger les ressources adéquat au fonctionnement de ce gestionnaire.
     * @return L'instance de ce gestionnaire.
     */
    public Players<T> load()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerHostListener(this), this.plugin);
        return this;
    }
}
