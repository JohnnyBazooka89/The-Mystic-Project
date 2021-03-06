package mysticmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import mysticmod.cards.HeavyStrike;

import java.util.Iterator;
import java.util.UUID;

public class MysticIncreaseMiscAction extends AbstractGameAction {
    private int miscIncrease;
    private UUID uuid;

    public MysticIncreaseMiscAction(UUID targetUUID, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        uuid = targetUUID;
    }

    public void update() {
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.uuid.equals(uuid)) {
                c.misc += miscIncrease;
                if (c instanceof HeavyStrike) {
                    ((HeavyStrike)c).updateBaseDamage();
                } else {
                    c.applyPowers();
                }
                c.baseBlock = c.misc;
                c.isBlockModified = false;
            }
        }

        for(var1 = GetAllInBattleInstances.get(uuid).iterator(); var1.hasNext(); c.baseBlock = c.misc) {// 31 34
            c = (AbstractCard)var1.next();
            c.misc += miscIncrease;
            if (c instanceof HeavyStrike) {
                ((HeavyStrike)c).updateBaseDamage();
            } else {
                c.applyPowers();
            }
        }

        isDone = true;
    }
}
