package net.treasure.effect.script.preset;

import lombok.AllArgsConstructor;
import net.treasure.effect.data.EffectData;
import net.treasure.effect.script.Script;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public class Preset extends Script {

    List<Script> scripts;

    @Override
    public boolean tick(Player player, EffectData data, int times) {
        for (Script script : scripts)
            if (!script.tick(player, data, times))
                return false;
        return true;
    }

    @Override
    public Preset clone() {
        return new Preset(scripts);
    }
}