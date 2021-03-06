/*
 * JavaFBP - A Java Implementation of Flow-Based Programming (FBP)
 * Copyright (C) 2009, 2016 J. Paul Morrison
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, see the GNU Library General Public License v3
 * at https://www.gnu.org/licenses/lgpl-3.0.en.html for more details.
 */

package com.jpaulmorrison.fbp.resourcekit.examples.networks;

/*
 * WARNING! 
 * 
 * I believe this will work reliably if the longest substream will fit within the connections on the shortest path
 * between the LoadBalance process and the SubstreamSensitiveMerge.  
 * 
 * If this rule is violated, you get unpredictable deadlocks.  
 * This test seems to crash about 1 time in 5 or 6!  SlowPass uses a random
 * interval between 0 and 500 msecs. 
 * 
 * It is therefore safer to use LoadBalance and SubstreamSensitiveMerge in different networks*!
 * 
 * You can test this by changing the capacities marked with arrows.  
 * 
 * The substreams generated by GenSS comprise Open bracket, 5 IPs, Close bracket.  
 * 
 */
	import com.jpaulmorrison.fbp.core.engine.Network;
	import com.jpaulmorrison.fbp.resourcekit.examples.components.GenSS;
	import com.jpaulmorrison.fbp.core.components.routing.LoadBalance;
	import com.jpaulmorrison.fbp.resourcekit.examples.components.SlowPass;
	import com.jpaulmorrison.fbp.core.components.routing.Passthru;
	import com.jpaulmorrison.fbp.core.components.misc.WriteToConsole;
	import com.jpaulmorrison.fbp.resourcekit.examples.components.CheckSequenceWithinSubstreams;
	import com.jpaulmorrison.fbp.core.components.routing.SubstreamSensitiveMerge;
	

	public class TestLoadBalanceWithSubstreams extends Network {
	  
	  @Override
	  protected void define() {
		boolean makeMergeSubstreamSensitive = true;
				
	    component("GenSS", GenSS.class);
	    component("LoadBalance", LoadBalance.class);		    
	    component("Passthru0", SlowPass.class);
	    component("Passthru1", SlowPass.class);
	    component("Passthru2", Passthru.class);
	    component("SlowPass", SlowPass.class);
	    component("Show", WriteToConsole.class);
	    component("Check", CheckSequenceWithinSubstreams.class);
	    
	    connect(component("GenSS"), port("OUT"), component("LoadBalance"), port("IN"), 4);
	    connect(component("LoadBalance"), port("OUT[0]"), component("Passthru0"), port("IN"), 7);  // <---
	    connect(component("LoadBalance"), port("OUT[1]"), component("Passthru1"), port("IN"), 7);  // <---
	    connect(component("LoadBalance"), port("OUT[2]"), component("Passthru2"), port("IN"), 7);  // <---
	    
		if (makeMergeSubstreamSensitive) {
			component("SubstreamSensitiveMerge", SubstreamSensitiveMerge.class);
			
			connect(component("Passthru0"), port("OUT"),
					component("SubstreamSensitiveMerge"), port("IN[0]"), 2);  // <---
			connect(component("Passthru1"), port("OUT"),
					component("SubstreamSensitiveMerge"), port("IN[1]"), 2);  // <---
			connect(component("Passthru2"), port("OUT"),
					component("SubstreamSensitiveMerge"), port("IN[2]"), 2);  // <---
			
			connect(component("SubstreamSensitiveMerge"), port("OUT"),
					component("SlowPass"), port("IN"), 4);
		} else {
			connect(component("Passthru0"), port("OUT"), component("SlowPass"),
					port("IN"), 2);
			connect(component("Passthru1"), port("OUT"), component("SlowPass"),
					port("IN"), 2);
			connect(component("Passthru2"), port("OUT"), component("SlowPass"),
					port("IN"), 2);
		}
	    //connect(component("SlowPass"), port("OUT"), component("Show"), port("IN"), 4);
	    connect(component("SlowPass"), port("OUT"), component("Check"), port("IN"), 4);
	    connect(component("Check"), port("OUT"), component("Show"), port("IN"), 4);
	    
	    initialize("100", component("GenSS"), port("COUNT"));
	    
	  }

	  public static void main(final String[] argv) throws Exception {
	    new TestLoadBalanceWithSubstreams().go();
	  }

	}

 
