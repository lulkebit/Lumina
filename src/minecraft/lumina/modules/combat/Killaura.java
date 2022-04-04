package lumina.modules.combat;

import lumina.events.Event;
import lumina.events.listeners.EventMotion;
import lumina.events.listeners.EventUpdate;
import lumina.modules.Module;
import lumina.util.settings.BooleanSetting;
import lumina.util.settings.ModeSetting;
import lumina.util.settings.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.stream.Collectors;

public class Killaura extends Module {
    private EntityLivingBase target;
    private long current, last;
    private float yaw, pitch;
    private boolean others;

    public NumberSetting range = new NumberSetting("Range", 4, 1, 6, 0.1);
    public NumberSetting delay = new NumberSetting("Delay", 10, 1, 20, 1);
    public BooleanSetting noSwing = new BooleanSetting("NoSwing", false);
    public ModeSetting test = new ModeSetting("Test", "One", "One", "Two", "Three");

    public Killaura() {
        super("Killaura", Keyboard.KEY_R, Category.COMBAT);
        this.addSettings(range, delay, noSwing, test);
    }


    @Override
    public void onEnable(){

    }

    @Override
    public void onDisable(){

    }

    @Override
    public void onEvent(Event event){
        if(event instanceof EventMotion){
            if(event.isPre()){
                EventMotion eventMotion = (EventMotion) event;

                target = getClosest(range.getValue());
                if(target == null)
                    return;
                updateTime();

                // ServerSide
                eventMotion.setYaw(getRotations(target)[0]);
                eventMotion.setPitch(getRotations(target)[1]);

                // ClientSide
                //mc.thePlayer.rotationYaw = (getRotations(target)[0]);
                //mc.thePlayer.rotationPitch = (getRotations(target)[1]);

                if(current - last > 1000 / (delay.getValue() * 100)){
                    attack(target);
                    resetTime();
                }
            }

            if(event.isPost()){
                if(target == null)
                    return;

            }
        }
    }

    public float[] getRotations(Entity entity){
        double deltaX = entity.posX + (entity.posX - entity.lastTickPosX) - mc.thePlayer.posX,
                deltaY = entity.posY - 3.5 + entity.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = entity.posZ + (entity.posZ - entity.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));

        if(deltaX < 0 && deltaZ < 0){
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if(deltaX > 0 && deltaZ > 0){
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }

        return new float[] { yaw, pitch };
    }

    private void attack(Entity entity){
        if(noSwing.isEnabled()){
            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
        } else {
            mc.thePlayer.swingItem();
        }
        // mc.playerController.attackEntity(mc.thePlayer, entity);
        mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
    }

    private void updateTime(){
        current = (System.nanoTime() / 1000000L);
    }

    private void resetTime(){
        last = (System.nanoTime() / 1000000L);
    }

    private EntityLivingBase getClosest(double range){
        double dist = range;
        EntityLivingBase target = null;
        for(Object object : mc.theWorld.loadedEntityList){
            Entity entity = (Entity) object;
            if(entity instanceof EntityLivingBase){
                EntityLivingBase player = (EntityLivingBase) entity;
                if(canAttack(player)){
                    if(!(player instanceof EntityMob) && !player.isDead && player.getHealth() > 0 || others){ // && (!(player instanceof EntityAnimal)
                        double currentDist = mc.thePlayer.getDistanceToEntity(player);
                        if(currentDist <= dist) {
                            dist = currentDist;
                            target = player;
                        }
                    }
                }
            }
        }
        return target;
    }

    private boolean canAttack(EntityLivingBase player){
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= range.getValue() && player.ticksExisted > 30;
    }    // mc.playerController.getBlockReachDistance()

}
