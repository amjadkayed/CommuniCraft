package com.communicate_craft.crafter;

import java.util.List;

public interface CrafterService {
    List<Crafter> getAllCrafters();
    Crafter addCrafter(Crafter crafter);
}
