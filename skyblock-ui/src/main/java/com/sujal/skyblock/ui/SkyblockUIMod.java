public class SkyblockUIMod implements ModInitializer {
    @Override
    public void onInitialize() {
         CommandRegistrationCallback.EVENT.register((dispatcher, registry, env) -> {
            dispatcher.register(CommandManager.literal("sbmenu")
                .executes(ctx -> {
                    ProfileMenu.open(ctx.getSource().getPlayerOrThrow());
                    return 1;
                }));
        });
    }
}
