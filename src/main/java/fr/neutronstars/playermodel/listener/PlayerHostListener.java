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
package fr.neutronstars.playermodel.listener;

import fr.neutronstars.playermodel.Players;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Ce listener permet d'écouter les connexions et déconnexions des joueurs et d'agir en fonction dans le gestionnaire {@link Players}
 */
public class PlayerHostListener implements Listener
{
    /**
     * Le gestionnaires des joueurs.
     */
    private final Players<?> players;

    /**
     * Permet de créer une nouvelle instance de ce listener
     * @param players le gestionnaire des joueurs.
     */
    public PlayerHostListener(Players<?> players)
    {
        this.players = players;
    }

    /**
     * Ecoute la connexion des joueurs et les ajoutes dans le gestionnaire {@link Players}
     * @param event l'objet appelé par {@link org.bukkit.Bukkit} lors de la connexion d'un joueur
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onJoin(PlayerJoinEvent event)
    {
        this.players.get(event.getPlayer());
    }

    /**
     * Ecoute la déconnexion des joueurs et les retires du gestionnaire {@link Players}
     * @param event l'objet appelé par {@link org.bukkit.Bukkit} lors de la déconnexion d'un joueur
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onQuit(PlayerQuitEvent event)
    {
        this.players.get(event.getPlayer()).ifPresent(this.players::leave);
    }
}
