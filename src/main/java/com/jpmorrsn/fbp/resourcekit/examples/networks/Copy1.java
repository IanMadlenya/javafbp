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
package com.jpmorrsn.fbp.resourcekit.examples.networks;


import com.jpmorrsn.fbp.core.components.misc.WriteToConsole;
import com.jpmorrsn.fbp.core.engine.Network;
import com.jpmorrsn.fbp.core.components.misc.Collate;
import com.jpmorrsn.fbp.core.components.misc.GenerateTestData;

/** 
 * Generate 100 test IPs and write them to the console
 *
 */
public class Copy1 extends Network {

 
  @Override
  protected void define() {
    // component("MONITOR", Monitor.class);

    connect(component("Generate", GenerateTestData.class), port("OUT"), component("Write", WriteToConsole.class), port("IN"));

    initialize("100", component("Generate"), port("COUNT"));

  }

  public static void main(final String[] argv) throws Exception {
    new Copy1().go();
  }
}
