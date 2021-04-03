package fr.neutronstars.playermodel;

import fr.neutronstars.api.model.Model;

/**
 * Cette class permet de créer de nouveau Model au {@link Player}.
 *
 * Il ne faudra pas oublié de l'enregistrer dans le gestionnaire {@link Players} grace à la méthode {@link Players#registerModel(Class)}
 */
public abstract class PlayerModel extends Model<Player>
{
    protected PlayerModel(Player entityModel)
    {
        super(entityModel);
    }
}
