import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

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

public class BlockListener implements Listener
{
    private final Test test;

    protected BlockListener(Test test)
    {
        this.test = test;
    }

    @EventHandler
    private void onBreak(BlockBreakEvent event)
    {
        this.test.getPlayers().get(event.getPlayer())
                .flatMap(player -> player.getModel(CustomPlayerModel.class))
                .ifPresent(m -> m.addBlockBreak(1));
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent event)
    {
        this.test.getPlayers().get(event.getPlayer())
                .flatMap(player -> player.getModel(CustomPlayerModel.class))
                .ifPresent(m -> m.addBlockPlace(1));
    }
}
