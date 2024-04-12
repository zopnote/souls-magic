package com.github.zopnote.soulsmagic.api.region;

import org.bukkit.Location;
import org.bukkit.Particle;

public class Circle {
  public Circle(Region region, Particle particle) {
    for (int degree = 0; degree < 360; degree++) {
      degree++;
      double radians = Math.toRadians(degree);
      double x = region.getRadius() * Math.cos(radians);
      double z = region.getRadius() * Math.sin(radians);
      Location particleLocation = region.getCenter();
      particleLocation.add(x, 0, z);
      particleLocation.getWorld().spawnParticle(particle, region.getCenter(), 1);
      particleLocation.subtract(x, 0, z);
    }
  }
}
