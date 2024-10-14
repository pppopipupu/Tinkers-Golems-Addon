package com.golems_tcon.proxy;

import com.golems_tcon.entity.*;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInitRenders() { 
		com.golems.proxies.ClientProxy.registerTextured(EntityArditeGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityBrownstoneGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityCGlassGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityCobaltGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityCongealedSlimeGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityFirewoodGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityKnightSlimeGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityManyullynGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntityPigironGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntitySearedGolem.class);
		com.golems.proxies.ClientProxy.registerTextured(EntitySilkyGolem.class);
		if(Loader.isModLoaded("futuremc")||Loader.isModLoaded("nb")) {
			com.golems.proxies.ClientProxy.registerTextured(EntityNetheriteGolem.class);
		}
	}
}
