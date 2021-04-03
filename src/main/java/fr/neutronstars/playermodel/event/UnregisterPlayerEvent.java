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
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Cette évènement est appelé lorsqu'un {@link Player} est supprimé.
 */
public class UnregisterPlayerEvent extends PlayerModelEvent implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;

    public UnregisterPlayerEvent(Player player)
    {
        super(player);
    }

    @Override
    public boolean isCancelled()
    {
        return this.cancelled || !this.getPlayer().isPresent();
    }

    @Override
    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers()
    {
        return UnregisterPlayerEvent.getHandlerList();
    }

    public static HandlerList getHandlerList()
    {
        return UnregisterPlayerEvent.handlers;
    }
}
