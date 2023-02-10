package ExtraUtilities.content;

import ExtraUtilities.ai.MinerPointAI;
import ExtraUtilities.worlds.entity.ability.TerritoryFieldAbility;
import ExtraUtilities.worlds.entity.ability.preventCheatingAbility;
import ExtraUtilities.worlds.entity.bullet.CtrlMissile;
import ExtraUtilities.worlds.entity.bullet.PercentDamage;
import ExtraUtilities.worlds.entity.bullet.SCSBullet;
import arc.Core;
import arc.func.Prov;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.ai.UnitCommand;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.abilities.EnergyFieldAbility;
import mindustry.entities.abilities.ShieldRegenFieldAbility;
import mindustry.entities.abilities.SuppressionFieldAbility;
import mindustry.entities.abilities.UnitSpawnAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.FlarePart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.unit.ErekirUnitType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.meta.Env;

import static ExtraUtilities.ExtraUtilitiesMod.*;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class EUUnitTypes {
    static {
        EntityMapping.nameMap.put(name("miner"), EntityMapping.idMap[36]);
        EntityMapping.nameMap.put(name("T2miner"), EntityMapping.idMap[36]);

        EntityMapping.nameMap.put(name("suzerain"), EntityMapping.idMap[4]);
        EntityMapping.nameMap.put(name("asphyxia"), EntityMapping.idMap[33]);
        EntityMapping.nameMap.put(name("apocalypse"), EntityMapping.idMap[3]);
        EntityMapping.nameMap.put(name("nihilo"), EntityMapping.idMap[20]);
    }

    public static UnitType
        miner, T2miner,
        //T6
        suzerain, asphyxia, apocalypse, nihilo;
    public static void load(){
        miner = new ErekirUnitType("miner"){{
            defaultCommand = UnitCommand.mineCommand;
            controller = u -> new MinerPointAI();

            flying = true;
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.5f;
            health = 100;
            engineSize = 1.8f;
            engineOffset = 5.7f;
            range = 50f;
            hitSize = 12f;
            itemCapacity = 20;
            isEnemy = false;
            payloadCapacity = 0;

            mineTier = 10;//据点决定tier
            mineSpeed = 1.6f;
            mineWalls = true;
            mineFloor = true;
            useUnitCap = false;
            logicControllable = false;
            playerControllable = false;
            allowedInPayloads = false;
            createWreck = false;
            envEnabled = Env.any;
            envDisabled = Env.none;
            hidden = true;
            targetable = false;

            //alwaysUnlocked = true;

            setEnginesMirror(
                    new UnitEngine(24 / 4f, -24 / 4f, 2.3f, 315f)
            );
        }};

        T2miner = new ErekirUnitType("T2miner"){{
            defaultCommand = UnitCommand.mineCommand;
            controller = u -> new MinerPointAI();

            flying = true;
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.5f;
            health = 100;
            engineSize = 2.6f;
            engineOffset = 9.8f;
            range = 50f;
            mineRange = 100f;
            hitSize = 16f;
            itemCapacity = 50;
            isEnemy = false;
            payloadCapacity = 0;

            mineTier = 10;
            mineSpeed = 3.2f;
            mineWalls = true;
            mineFloor = true;
            useUnitCap = false;
            logicControllable = false;
            playerControllable = false;
            allowedInPayloads = false;
            createWreck = false;
            envEnabled = Env.any;
            envDisabled = Env.none;
            hidden = true;
            targetable = false;

            //alwaysUnlocked = true;

            setEnginesMirror(
                    new UnitEngine(40 / 4f, -40 / 4f, 3f, 315f)
            );
        }};

        //T6
        suzerain = new UnitType("suzerain"){{
            armor = 20;
            speed = 0.3f;
            hitSize = 40;
            rotateSpeed = 1.8f;
            canDrown = false;
            mechStepParticles = true;
            stepShake = 1;
            mechFrontSway = 1.9f;
            mechSideSway = 0.6f;
            singleTarget = true;
            health = 63000;
            itemCapacity = 240;
            ammoType = new ItemAmmoType(Items.thorium);
            abilities.add(new TerritoryFieldAbility(20 * 8, 90, 210){{
                open = true;
            }}, new ShieldRegenFieldAbility(100, 1000, 60 * 4, 20 * 8), new preventCheatingAbility());
            weapons.add(
                    new Weapon(name("suzerain-weapon")){{
                        shake = 5;
                        shootY = 13;
                        top = false;
                        shoot.shots = 5;
                        inaccuracy = 1;
                        shoot.shotDelay = 3;
                        ejectEffect = Fx.casing4;
                        //bullet = ((ItemTurret)Blocks.spectre).ammoTypes.get(Items.thorium);
                        bullet = new BasicBulletType(13f, 66){{
                            pierce = true;
                            pierceCap = 10;
                            width = 14f;
                            height = 33f;
                            lifetime = 24f;
                            shootEffect = Fx.shootBig;
                            fragVelocityMin = 0.4f;

                            hitEffect = Fx.blastExplosion;
                            splashDamage = 18f;
                            splashDamageRadius = 13f;

                            fragBullets = 3;
                            fragLifeMin = 0f;
                            fragRandomSpread = 30f;

                            fragBullet = new BasicBulletType(9f, 15){{
                                width = 10f;
                                height = 10f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 3;

                                lifetime = 20f;
                                hitEffect = Fx.flakExplosion;
                                splashDamage = 15f;
                                splashDamageRadius = 10f;
                            }};
                        }};
                        x = 30;
                        y = 1;
                        shootCone = 80f;
                        shootSound = Sounds.bang;
                        reload = 24;
                        recoil = 5;

                        rotate = true;
                        rotateSpeed = 0.6f;
                        rotationLimit = 20f;
                    }},
                    new Weapon(name("suzerain-weapon2")){{
                        shake = 4;
                        shootY = 7;
                        shoot = new ShootSpread(2, 10f);
                        bullet = new FlakBulletType(8f, 40f){{
                            sprite = "missile-large";

                            lifetime = 40f;
                            width = 12f;
                            height = 22f;

                            hitSize = 7f;
                            shootEffect = Fx.shootSmokeSquareBig;
                            smokeEffect = Fx.shootSmokeDisperse;
                            ammoMultiplier = 1;
                            hitColor = backColor = trailColor = lightningColor = Pal.surge;
                            frontColor = Color.white;
                            trailWidth = 3f;
                            trailLength = 12;
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                            buildingDamageMultiplier = 0.5f;

                            trailEffect = Fx.colorSpark;
                            trailRotation = true;
                            trailInterval = 3f;
                            lightning = 1;
                            lightningCone = 15f;
                            lightningLength = 20;
                            lightningLengthRand = 30;
                            lightningDamage = 20f;

                            homingPower = 0.17f;
                            homingDelay = 10f;
                            homingRange = 160f;

                            explodeRange = 0f;
                            explodeDelay = 0f;

                            flakInterval = 20f;
                            despawnShake = 3f;

                            fragBullet = new LaserBulletType(40f){{
                                colors = new Color[]{Pal.surge.cpy().a(0.4f), Pal.surge, Color.white};
                                buildingDamageMultiplier = 0.4f;
                                width = 19f;
                                hitEffect = Fx.hitLancer;
                                sideAngle = 175f;
                                sideWidth = 1f;
                                sideLength = 40f;
                                lifetime = 22f;
                                drawSize = 400f;
                                length = 180f;
                                pierceCap = 2;
                            }};

                            fragSpread = fragRandomSpread = 0f;

                            splashDamage = 0f;
                            hitEffect = Fx.hitSquaresColor;
                            collidesGround = true;
                        }};
                        rotate = true;
                        rotateSpeed = 2;
                        x = 15;
                        y = -2;
                        shootSound = Sounds.railgun;
                        reload = 27;
                        recoil = 4;
                    }}
            );
        }};

        asphyxia = new UnitType("asphyxia"){{
            armor = 17;
            flying = false;
            speed = 0.4f;
            hitSize = 33;
            lightRadius = 160;
            rotateSpeed = 1.8f;
            stepShake = 1.5f;
            health = 54000;
            buildSpeed = 1.5f;
            itemCapacity = 180;

            legCount = 8;
            legLength = 80;
            legBaseOffset = 8;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legExtension = -22;
            legLengthScl = 0.93f;
            rippleScale = 3.4f;
            legSpeed = 0.18f;

            canDrown = false;
            hovering = true;
            allowLegStep = true;
            groundLayer = Layer.legUnit;
            ammoType = new PowerAmmoType(3000);
            legSplashDamage = 100;
            legSplashRange = 64;
            
            BulletType sapper = new SapBulletType(){{
                sapStrength = 1.5f;
                length = 96;
                damage = 50;
                shootEffect = Fx.shootSmall;
                hitColor = Color.valueOf("bf92f9");
                color = Color.valueOf("bf92f9");
                despawnEffect = Fx.none;
                width = 0.55f;
                lifetime = 30;
                knockback = -1;
            }};
            
            BulletType asl = new ContinuousLaserBulletType(){{
                damage = 106;
                length = 240;
                width = 7;
                hitEffect = Fx.sapExplosion;
                drawSize = 420;
                lifetime = 160;
                despawnEffect = Fx.smokeCloud;
                smokeEffect = Fx.none;
                chargeEffect = new Effect(40, 100, e -> {
                        Draw.color(Pal.sapBullet);
                    Lines.stroke(e.fin() * 2);
                    Lines.circle(e.x, e.y, e.fout() * 50);
                });
                status = StatusEffects.wet;
                incendChance = 0.075f;
                incendSpread = 5;
                incendAmount = 1;
                colors = new Color[]{Pal.sapBullet, Pal.sapBullet, Pal.sapBulletBack};
            }

                @Override
                public void update(Bullet b) {
                    super.update(b);
                    if(b.timer.get(8)){
                        Lightning.create(b.team, Pal.sapBullet, damage/2, b.x, b.y, b.rotation() + (4 - Mathf.range(8)), (int)(length/6));
                    }
                }
            };

            SCSBullet lineBullet = new SCSBullet(){{
                damage = 150;
            }};

            weapons.add(
                    new Weapon(name("asphyxia-main")){{
                        top = false;
                        shake = 4;
                        shootY = 13;
                        reload = 150;
                        cooldownTime = 200;
                        shootSound = Sounds.beam;
                        bullet = asl;
                        mirror = false;
                        continuous = true;
                        rotate = true;
                        rotateSpeed = 3;
                        rotationLimit = 70f;

                        x = 0;
                        y = -12;
                        chargeSound = Sounds.lasercharge2;
                        shoot.firstShotDelay = 49;
                        recoil = 3;
                        parentizeEffects = true;
                    }},

                    new Weapon(name("asphyxia-l")){{
                        top = false;
                        x = 14;
                        y = -5;
                        reload = 60;
                        rotate = true;
                        bullet = lineBullet;
                        shootSound = Sounds.bang;
                    }

                        @Override
                        public void addStats(UnitType u, Table t) {
                            super.addStats(u, t);
                            t.row();
                            t.add(Core.bundle.format("unit.extra-utilities-asphyxia.weaponStat1", lineBullet.maxFind));
                        }
                    },
                    
                    new Weapon(name("asphyxia-f")){{
                        top = false;
                        x = 9;
                        y = 8;
                        reload = 9;
                        rotate = true;
                        autoTarget = true;
                        controllable = false;
                        bullet = sapper;
                        shootSound = Sounds.sap;
                    }},
                    new Weapon(name("asphyxia-f")){{
                        top = false;
                        x = 14;
                        y = 6;
                        reload = 9;
                        rotate = true;
                        autoTarget = true;
                        controllable = false;
                        bullet = sapper;
                        shootSound = Sounds.sap;
                    }}
            );
        }};
        apocalypse = new UnitType("apocalypse"){{
            armor = 18;
            flying = true;
            speed = 0.51f;
            hitSize = 62;
            accel = 0.04f;
            rotateSpeed = 1;
            baseRotateSpeed = 20;
            drag = 0.018f;
            health = 60000;
            lowAltitude = true;
            itemCapacity = 320;
            engineOffset = 41;
            engineSize = 11;
            targetFlags = UnitTypes.eclipse.targetFlags;
            immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting);
            ammoType = new ItemAmmoType(Items.pyratite);

            abilities.add(new UnitSpawnAbility(UnitTypes.crawler, 60*10, 17, -27.5f), new UnitSpawnAbility(UnitTypes.crawler, 60*10, -17, -27.5f));
            abilities.add(new EnergyFieldAbility(60f, 90f, 192f){{
                color = Color.valueOf("FFA665");
                status = StatusEffects.unmoving;
                statusDuration = 60f;
                maxTargets = 30;
                healPercent = 0.8f;
            }});

            PercentDamage bubble = new PercentDamage(){{
                damage = 13;
                status = StatusEffects.sapped;
            }};

            weapons.add(
                    new Weapon(name("apocalypse-m1")){{
                        top = false;
                        shake = 3;
                        shootY = 2;
                        bullet = new MissileBulletType(3.7f, 36){{
                            width = 8f;
                            height = 8f;
                            shrinkY = 0f;
                            splashDamageRadius = 30f;
                            splashDamage = 72;
                            hitEffect = Fx.blastExplosion;
                            despawnEffect = Fx.blastExplosion;
                            status = StatusEffects.blasted;
                            statusDuration = 60f;
                        }};
                        rotate = true;
                        rotateSpeed = 4;
                        x = 35;
                        y = 23;
                        shoot.shotDelay = 3;
                        xRand = 2;
                        shoot.shots = 3;
                        inaccuracy = 3;
                        ejectEffect = Fx.none;
                        shootSound = Sounds.missile;
                        reload = 30;
                        recoil = 2;
                    }},
                    new Weapon(name("apocalypse-m1")){{
                        top = false;
                        shake = 3;
                        shootY = 2;
                        bullet = new MissileBulletType(3.7f, 36){{
                            width = 8f;
                            height = 8f;
                            shrinkY = 0f;
                            splashDamageRadius = 30f;
                            splashDamage = 72;
                            hitEffect = Fx.blastExplosion;
                            despawnEffect = Fx.blastExplosion;
                            status = StatusEffects.blasted;
                            statusDuration = 60f;
                        }};
                        rotate = true;
                        rotateSpeed = 4;
                        x = 30;
                        y = -27;
                        shoot.shotDelay = 3;
                        xRand = 2;
                        shoot.shots = 3;
                        inaccuracy = 3;
                        ejectEffect = Fx.none;
                        shootSound = Sounds.missile;
                        reload = 30;
                        recoil = 2;
                    }},
                    new Weapon(name("apocalypse-bubble")){{
                        top = false;
                        shootY = 6;
                        bullet = bubble;
                        rotate = true;
                        rotateSpeed = 8;
                        x = 14;
                        y = 29;
                        shoot.shotDelay = 3;
                        xRand = 2;
                        shoot.shots = 5;
                        inaccuracy = 1;
                        ejectEffect = Fx.none;
                        reload = 6;
                        recoil = 1;
                    }

                        @Override
                        public void addStats(UnitType u, Table t) {
                            super.addStats(u, t);
                            t.row();
                            t.add(Core.bundle.format("stat." + name("percentDamage"), bubble.percent * 100));
                        }
                    },
                    new Weapon(name("apocalypse-weapon")){{
                        top = false;
                        shake = 4;
                        shootY = 16;
                        reload = 90;
                        bullet = new RailBulletType(){{
                            shootEffect = Fx.railShoot;
                            length = 400;
                            pierceEffect = Fx.railHit;
                            hitEffect = Fx.massiveExplosion;
                            smokeEffect = Fx.shootBig2;
                            damage = 986;
                            pierceDamageFactor = 0.5f;
                            pointEffectSpace = 60f;
                            pointEffect = Fx.railTrail;
                        }};
                        rotate = true;
                        rotateSpeed = 2;
                        shootSound = Sounds.railgun;
                        x = 28;
                        y = -3;
                    }}
            );
        }};

        nihilo = new UnitType("nihilo"){{
            float spawnTime = 60 * 14;
            trailLength = 70;
            waveTrailX = 25f;
            waveTrailY = -32f;
            trailScl = 3.5f;
            abilities.add(new TerritoryFieldAbility(220, -1, 150){{
                active = false;
            }});
            abilities.add(new ShieldRegenFieldAbility(100, 600, 60 * 4, 200));
            abilities.add(new UnitSpawnAbility(UnitTypes.flare, spawnTime, 9.5f, -35.5f), new UnitSpawnAbility(UnitTypes.flare, spawnTime, -9.5f, -35.5f), new UnitSpawnAbility(UnitTypes.zenith, spawnTime * 5, 29, -25), new UnitSpawnAbility(UnitTypes.zenith, spawnTime * 5, -29, -25));
            armor = 19;
            drag = 0.2f;
            flying = false;
            speed = 0.6f;
            accel = 0.2f;
            hitSize = 60;
            rotateSpeed = 0.9f;
            health = 61000;
            itemCapacity = 350;
            ammoType = new ItemAmmoType(Items.surgeAlloy);
            BulletType air = new PointBulletType(){{
                trailEffect = Fx.railTrail;
                splashDamage = 520;
                splashDamageRadius = 88;
                shootEffect = new Effect(10, e -> {
                    Draw.color(Color.white, Color.valueOf("767a84"), e.fin());
                    Lines.stroke(e.fout() * 2 + 0.2f);
                    Lines.circle(e.x, e.y, e.fin() * 28);
                });
                hitEffect = despawnEffect = new Effect(15, e -> {
                    Draw.color(Color.white, Color.valueOf("767a84"), e.fin());
                    Lines.stroke(e.fout() * 2 + 0.2f);
                    Lines.circle(e.x, e.y, e.fin() * splashDamageRadius);
                });
                trailSpacing = 40;
                damage = 100;
                collidesGround = false;
                lifetime = 1;
                speed = 320;
                status = StatusEffects.shocked;
                hitShake = 6;
            }};
            BulletType r = new RailBulletType(){{
                shootEffect = Fx.railShoot;
                length = 500;
                pierceEffect = Fx.railHit;
                hitEffect = Fx.massiveExplosion;
                smokeEffect = Fx.shootBig2;
                damage = 1350;
                pointEffectSpace = 30f;
                pointEffect = Fx.railTrail;
                pierceDamageFactor = 0.6f;
            }};
            weapons.add(
                    new PointDefenseWeapon(name("nihilo-defense")){{
                        top = false;
                        x = 0;
                        y = -17;
                        reload = 6;
                        targetInterval = 8;
                        targetSwitchInterval = 8;
                        mirror = false;
                        shootSound = Sounds.lasershoot;
                        bullet = new BulletType(){{
                            shootEffect = Fx.sparkShoot;
                            hitEffect = Fx.pointHit;
                            maxRange = 288;
                            damage = 40;
                        }};
                    }},
                    new Weapon(name("nihilo-m")){{
                        top = false;
                        shake = 3;
                        shootY = 2;
                        bullet = new MissileBulletType(3.7f, 30){{
                            lifetime = 60;
                            width = 8f;
                            height = 8f;
                            shrinkY = 0f;
                            splashDamageRadius = 32f;
                            splashDamage = 30f * 1.4f;
                            hitEffect = Fx.blastExplosion;
                            despawnEffect = Fx.blastExplosion;
                            lightningDamage = 12;
                            lightning = 3;
                            lightningLength = 10;
                        }};
                        rotate = true;
                        rotateSpeed = 4;
                        x = 24;
                        y = 1;
                        shoot.shotDelay = 1;
                        xRand = 10;
                        shoot.shots = 6;
                        inaccuracy = 4;
                        ejectEffect = Fx.none;
                        shootSound = Sounds.missile;
                        reload = 36;
                        recoil = 2;
                    }},
                    new Weapon(name("nihilo-a")){{
                        shake = 4;
                        shootY = 6;
                        top = false;
                        shoot.shots = 4;
                        inaccuracy = 12;
                        velocityRnd = 0.2f;
                        bullet = new ArtilleryBulletType(2.8f, 30){{
                            hitEffect = Fx.blastExplosion;
                            knockback = 0.8f;
                            lifetime = 100;
                            width = 14;
                            height = 14;
                            collidesTiles = false;
                            ammoMultiplier = 3;
                            splashDamageRadius = 28;
                            splashDamage = 38;
                            frontColor = Pal.surge;
                            lightningDamage = 12;
                            lightning = 2;
                            lightningLength = 8;
                        }};
                        rotate = true;
                        rotateSpeed = 4;
                        x = 14.5f;
                        y = -10;
                        shootSound = Sounds.artillery;
                        reload = 60;
                        recoil = 4;
                    }},
                    new Weapon(name("nihilo-air")){{
                        shake = 5;
                        shootY = 9;
                        bullet = air;
                        mirror = false;
                        top = false;
                        rotate = true;
                        rotateSpeed = 8;
                        controllable = false;
                        autoTarget = true;
                        x = 0;
                        y = 38;
                        ejectEffect = Fx.none;
                        shootSound = Sounds.railgun;
                        reload = 180;
                        recoil = 4;
                    }}
            );
            weapons.add(
                    new Weapon(name("nihilo-main")){{
                        shake = 6;
                        shootY = 23;
                        bullet = r;
                        mirror = false;
                        top = false;
                        rotate = true;
                        rotateSpeed = 2;
                        x = 0;
                        y = 5;
                        ejectEffect = Fx.none;
                        shootSound = Sounds.railgun;
                        reload = 110;
                        cooldownTime = 90;
                        recoil = 5;
                    }}
            );
        }};

        //override
        UnitTypes.quell.health = 6500;
        UnitTypes.quell.armor = 7;
        UnitTypes.quell.weapons.get(0).bullet = new CtrlMissile("quell-missile", -1, -1){{
            shootEffect = Fx.shootBig;
            smokeEffect = Fx.shootBigSmoke2;
            speed = 4.3f;
            keepVelocity = false;
            maxRange = 6f;
            lifetime = 60f * 1.6f;
            damage = 110;
            splashDamage = 110;
            splashDamageRadius = 25;
            buildingDamageMultiplier = 0.8f;
            hitEffect = despawnEffect = Fx.massiveExplosion;
            trailColor = Pal.sapBulletBack;
        }};
        UnitTypes.quell.weapons.get(0).shake = 1;

        UnitTypes.disrupt.weapons.get(0).bullet = new CtrlMissile("disrupt-missile", -1, -1){{
            shootEffect = Fx.sparkShoot;
            smokeEffect = Fx.shootSmokeTitan;
            hitColor = Pal.suppress;
            maxRange = 5f;
            speed = 4.6f;
            keepVelocity = false;
            homingDelay = 10f;
            trailColor = Pal.sapBulletBack;
            trailLength = 8;
            hitEffect = despawnEffect = new ExplosionEffect(){{
                lifetime = 50f;
                waveStroke = 5f;
                waveLife = 8f;
                waveColor = Color.white;
                sparkColor = smokeColor = Pal.suppress;
                waveRad = 40f;
                smokeSize = 4f;
                smokes = 7;
                smokeSizeBase = 0f;
                sparks = 10;
                sparkRad = 40f;
                sparkLen = 6f;
                sparkStroke = 2f;
            }};
            damage = 150;
            splashDamage = 150;
            splashDamageRadius = 25;
            buildingDamageMultiplier = 0.8f;

            parts.add(new ShapePart(){{
                layer = Layer.effect;
                circle = true;
                y = -3.5f;
                radius = 1.6f;
                color = Pal.suppress;
                colorTo = Color.white;
                progress = PartProgress.life.curve(Interp.pow5In);
            }});
        }};
        UnitTypes.disrupt.weapons.get(0).shake = 1f;

        UnitTypes.anthicus.weapons.get(0).bullet = new CtrlMissile("anthicus-missile", -1, -1){{
            shootEffect = new MultiEffect(Fx.shootBigColor, new Effect(9, e -> {
                color(Color.white, e.color, e.fin());
                stroke(0.7f + e.fout());
                Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
            }), new WaveEffect(){{
                colorFrom = colorTo = Pal.techBlue;
                sizeTo = 15f;
                lifetime = 12f;
                strokeFrom = 3f;
            }});

            smokeEffect = Fx.shootBigSmoke2;
            speed = 3.7f;
            keepVelocity = false;
            inaccuracy = 2f;
            maxRange = 6;
            trailWidth = 2;
            trailColor = Pal.techBlue;
            low = true;
            absorbable = true;

            damage = 110;
            splashDamage = 110;
            splashDamageRadius = 25;
            buildingDamageMultiplier = 0.8f;

            despawnEffect = hitEffect = new MultiEffect(Fx.massiveExplosion, new WrapEffect(Fx.dynamicSpikes, Pal.techBlue, 24f), new WaveEffect(){{
                colorFrom = colorTo = Pal.techBlue;
                sizeTo = 40f;
                lifetime = 12f;
                strokeFrom = 4f;
            }});

            parts.add(new FlarePart(){{
                progress = PartProgress.life.slope().curve(Interp.pow2In);
                radius = 0f;
                radiusTo = 35f;
                stroke = 3f;
                rotation = 45f;
                y = -5f;
                followRotation = true;
            }});
        }};
        UnitTypes.anthicus.weapons.get(0).shake = 2;
        UnitTypes.anthicus.weapons.get(0).reload = 120;

        UnitTypes.tecta.weapons.get(0).bullet.damage = 0;
        UnitTypes.tecta.weapons.get(0).bullet.splashDamage = 95;
        UnitTypes.tecta.health = 9000;
    }
}
