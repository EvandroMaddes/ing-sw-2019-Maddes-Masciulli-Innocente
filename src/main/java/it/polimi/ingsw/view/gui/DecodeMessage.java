package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.game_components.cards.Weapon;
import it.polimi.ingsw.model.game_components.cards.weapons.Zx2;
import it.polimi.ingsw.model.player.Character;
import it.polimi.ingsw.view.cli.graph.Color;

import java.io.File;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class DecodeMessage {

    private Map<Character, Path> mapCharacterPlayerboard = new EnumMap<Character, Path>(Character.class);
    private Map<String,Path> mapWeapon = new HashMap<String,Path>();

    public DecodeMessage() {
        Path dstructor = getPath("playerBoard/yellowPlayerBoard.png");
        Path banshee = getPath("playerBoard/bluePlayerBoard.png");
        Path violet = getPath("playerBoard/violetPlayerBoard.png");
        Path dozer = getPath("playerBoard/greyPlayerBoard.png");
        Path sprog = getPath("playerBoard/greenPlayerBoard.png");
        //setting character  e relativa playerboard
        mapCharacterPlayerboard.put(Character.D_STRUCT_OR, dstructor);
        mapCharacterPlayerboard.put(Character.BANSHEE, banshee);
        mapCharacterPlayerboard.put(Character.DOZER, dozer);
        mapCharacterPlayerboard.put(Character.VIOLET,violet);
        mapCharacterPlayerboard.put(Character.SPROG, sprog);

        Path cyberBlade = getPath("cards/weapon/cyberBlade.png");
        Path electroscythe = getPath("cards/weapon/electroscythe.png");
        Path flamethrower = getPath("cards/weapon/flamethower.png");
        Path furnace = getPath("cards/weapon/furnace.png");
        Path grenadaLauncher = getPath("cards/weapon/grenadaLauncher.png");
        Path heatseeker = getPath("cards/weapon/heatseeker.png");
        Path hellion = getPath("cards/weapon/hellion.png");
        Path lockRifle = getPath("cards/weapon/lockRifle.png");
        Path machineGun = getPath("cards/weapon/machineGun.png");
        Path plasmaGun = getPath("cards/weapon/plasmaGun.png");
        Path powerGlove= getPath("cards/weapon/powerGlove.png");
        Path railgun = getPath("cards/weapon/railgun.png");
        Path rocketLauncher= getPath("cards/weapon/rocketLauncher.png");
        Path shockwave = getPath("cards/weapon/shockwave.png");
        Path shotgun= getPath("cards/weapon/shotgun.png");
        Path sledgeHammer= getPath("cards/weapon/sledgeHammer.png");
        Path thor= getPath("cards/weapon/thor.png");
        Path tractorBeam= getPath("cards/weapon/tractorBeam.png");
        Path vortexCannon= getPath("cards/weapon/vortexCannon.png");
        Path whisper= getPath("cards/weapon/whisper.png");
        Path zx2= getPath("cards/weapon/zx2.png");
        Path unload= getPath("cards/weapon/unload.png");

        //setting weapon e relativa carta
        mapWeapon.put( "CYBER BLADE",cyberBlade);
        mapWeapon.put( "ELECTROSCYTHE",electroscythe);
        mapWeapon.put( "FLAMETHROWER",flamethrower);
        mapWeapon.put( "FURNACE",furnace);
        mapWeapon.put( "GRENADE LAUNCHER",grenadaLauncher);
        mapWeapon.put( "HEATSEEKER",heatseeker);
        mapWeapon.put( "HELLION",hellion);
        mapWeapon.put( "LOCK RIFLE",lockRifle);
        mapWeapon.put( "MACHINE GUN",machineGun);
        mapWeapon.put( "PLASMA GUN",plasmaGun);
        mapWeapon.put( "POWER GLOVE",powerGlove);
        mapWeapon.put( "RAILGUN",railgun);
        mapWeapon.put( "ROCKET LAUNCHER",rocketLauncher);
        mapWeapon.put( "SHOCKWAVE",shockwave);
        mapWeapon.put( "SHOTGUN",shotgun);
        mapWeapon.put( "SLEDGEHAMMER",sledgeHammer);
        mapWeapon.put( "T.H.O.R.",thor);
        mapWeapon.put( "TRACTOR BEAM",tractorBeam);
        mapWeapon.put( "VORTEX CANNON",vortexCannon);
        mapWeapon.put( "WHISPER",whisper);
        mapWeapon.put( "ZX-2", zx2);

        // TODO: 30/06/2019  ammo, map, power up,  

    }

public Path getPath(String convert){
    Path path = null;
    File resources = new File(convert);
    try {
        path = resources.toPath();
    }catch (NullPointerException e){
        System.out.println("errore acceso file");
    }
    return path;
    }

    public Map<Character, Path> getMapCharacterPlayerboard() {
        return mapCharacterPlayerboard;
    }

    public Map<String, Path> getMapWeapon() {
        return mapWeapon;
    }
}
