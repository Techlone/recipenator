package recipenator.compatibility.nei;

import codechicken.nei.api.IConfigureNEI;

public class NEI_RNator_Config implements IConfigureNEI {
    @Override
    public void loadConfig() {
        RecipeHandlers.registerRecipe();
    }

    @Override
    public String getName() {
        return "NEI Recipenator";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
