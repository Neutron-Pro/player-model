import fr.neutronstars.playermodel.Player;
import fr.neutronstars.playermodel.PlayerModel;

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

public class CustomPlayerModel extends PlayerModel
{
    private int blockBreak;
    private int blockPlace;

    public CustomPlayerModel(Player entityModel)
    {
        super(entityModel);
    }

    public int getBlockBreak()
    {
        return this.blockBreak;
    }

    public void setBlockBreak(int blockBreak)
    {
        this.blockBreak = blockBreak;
    }

    public void addBlockBreak(int blockBreak)
    {
        this.blockBreak += blockBreak;
    }

    public void removeBlockBreak(int blockBreak)
    {
        this.blockBreak -= blockBreak;
    }

    public int getBlockPlace()
    {
        return this.blockPlace;
    }

    public void setBlockPlace(int blockPlace)
    {
        this.blockPlace = blockPlace;
    }

    public void addBlockPlace(int blockPlace)
    {
        this.blockPlace += blockPlace;
    }

    public void removeBlockPlace(int blockPlace)
    {
        this.blockPlace -= blockPlace;
    }
}
