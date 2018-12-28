package mysticmod.actions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import mysticmod.vfx.MagicMissileEffect;

public class MagicMissileAction extends AbstractGameAction {
    private DamageInfo info;
    private int damageCount = 0;
    public boolean doDamage = false;
    private int projectileCount;
    private int projectilesFired;
    private float projectileTimer = 0.0f;
    private float projectileDelay;

    public MagicMissileAction(AbstractCreature target, DamageInfo info, int projectileCount, float projectileDelay) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.projectileCount = projectileCount;
        this.projectileDelay = projectileDelay;
    }

    @Override
    public void update() {
        projectileTimer -= Gdx.graphics.getDeltaTime();
        if (this.shouldCancelAction() && this.info.type != DamageInfo.DamageType.THORNS) {
            this.isDone = true;
            return;
        }
        if (projectilesFired < projectileCount && projectileTimer <= 0.0f) {
            AbstractDungeon.effectList.add(
                    new MagicMissileEffect(source.dialogX + 80.0f * Settings.scale, source.dialogY - 50.0f * Settings.scale, target.hb.cX, target.hb.cY, this)
            );
            projectilesFired++;
            projectileTimer = projectileDelay;
        }
        if (doDamage && damageCount < projectileCount) {
            damageCount++;
            doDamage = false;
            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            FlashAtkImgEffect bluePoison = new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.POISON);
            ReflectionHacks.setPrivateInherited(bluePoison, FlashAtkImgEffect.class, "color", Color.CYAN.cpy());
            AbstractDungeon.effectList.add(bluePoison);
            this.target.tint.color = Color.BLUE.cpy();
            this.target.tint.changeColor(Color.WHITE.cpy());
            this.target.damage(this.info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        if (damageCount == projectileCount || target.isDying) {
            this.isDone = true;
            return;
        }
    }
}