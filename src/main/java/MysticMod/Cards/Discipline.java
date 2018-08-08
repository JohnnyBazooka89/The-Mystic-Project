package MysticMod.Cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import MysticMod.Patches.AbstractCardEnum;
import MysticMod.Powers.DisciplinePower;

import basemod.abstracts.CustomCard;

public class Discipline
        extends CustomCard {
    public static final String ID = "MysticMod:Discipline";
    public static final String NAME = "Discipline";
    public static final String DESCRIPTION = "Begin each turn as if you had already played one Spell and one Technique.";
    public static final String IMG_PATH = "MysticMod/images/cards/discipline.png";
    private static final int COST = 2;
    public static final int UPGRADE_COST = 1;

    public Discipline() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new DisciplinePower(p, 1), 1));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Discipline();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}