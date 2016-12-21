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


import com.jpaulmorrison.fbp.core.components.misc.WriteToConsole;
import com.jpaulmorrison.fbp.core.engine.Network;


/** 
 * Generate 100 test IPs and write them to the console
 *
 */
public class Copy1 extends Network {

 
  @Override
  protected void define() {
    // component("MONITOR", Monitor.class);

    connect(component("Generate", com.jpaulmorrison.fbp.resourcekit.examples.components.GenerateSlowly.class), port("OUT"), component("Passthru", com.jpaulmorrison.fbp.core.components.routing.Passthru.class), port("IN"));
    connect(component("Passthru"), port("OUT"), component("Write", WriteToConsole.class), port("IN"));

    initialize("200", component("Generate"), port("CONFIG"));

  }

  public static void main(final String[] argv) throws Exception {
	  //for (int i = 0; i < 50; i++)
         new Copy1().go();
  }
}
