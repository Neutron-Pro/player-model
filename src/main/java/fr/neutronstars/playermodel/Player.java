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

import fr.neutronstars.api.model.EntityModel;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;

/**
 * Le joueur créé par le gestionnaire {@link Players} et qui est relié à un {@link org.bukkit.entity.Player} de {@link Bukkit}
 */
public class Player extends EntityModel
{
    /**
     * L'identifiant unique du joueur.
     */
    protected final UUID uuid;

    /**
     * Le nom du joueur.
     */
    protected String name;

    /**
     * Permet de créer une nouvelle instance de {@link Player}.
     * @param uuid l'identifiant du joueur.
     * @param name le nom du joueur.
     */
    protected Player(UUID uuid, String name)
    {
        this.uuid = uuid;
        this.name = name;
    }

    /**
     * Permet de récupérer un {@link Optional} avec le {@link org.bukkit.entity.Player} connecté. Si le joueur n'est pas connecté
     * alors l'Optional sera vide.
     * @return un {@link Optional} avec l'instance de {@link org.bukkit.entity.Player} s'il est connecté.
     */
    public Optional<org.bukkit.entity.Player> get()
    {
        return Optional.ofNullable(Bukkit.getPlayer(this.getUniqueId()));
    }

    /**
     * Permet de récupérer l'identifiant du joueur.
     * @return l'identifiant du joueur.
     */
    public UUID getUniqueId()
    {
        return this.uuid;
    }

    /**
     * Permet de récupérer le nom du joueur.
     * @return le nom du joueur.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Change le nom du joueur. Attention, celui-ci ne le changera pas dans la tablist ou au dessus de la tête de celui-ci.
     * @param name le nouveau nom du joueur.
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
