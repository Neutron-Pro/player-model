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
package fr.neutronstars.playermodel.event;

import fr.neutronstars.playermodel.Player;
import org.bukkit.event.Event;

import java.util.Optional;

public abstract class PlayerModelEvent extends Event
{
    private Player player;

    protected PlayerModelEvent(Player player)
    {
        this.player = player;
    }

    public Optional<Player> getPlayer()
    {
        return Optional.ofNullable(this.player);
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }
}
