package MysticMod.Cards.Cantrips;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import MysticMod.Patches.AbstractCardEnum;
import MysticMod.Powers.SpellsPlayed;

import basemod.abstracts.CustomCard;

public class Prestidigitation
        extends CustomCard {
    public static final String ID = "MysticMod:Prestidigitation";
    public static final String NAME = "Prestidigitation";
    public static final String DESCRIPTION = "Cantrip. NL Apply !B! block. Draw a card.";
    public static final String IMG_PATH = "MysticMod/images/cards/prestidigitation.png";
    private static final int COST = 0;
    private static final int BLOCK_AMT = 2;
    private static final int UPGRADE_PLUS_BLK = 2;

    public Prestidigitation() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.SPECIAL, AbstractCard.CardTarget.SELF);
        this.block = this.baseBlock = BLOCK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DrawCardAction(p, 1));
        //cantrip functionality
        if (
                !(p.hasPower(SpellsPlayed.POWER_ID))
                        ||
                        (p.hasPower(SpellsPlayed.POWER_ID) && (p.getPower(SpellsPlayed.POWER_ID).amount == 1))
        ) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpellsPlayed(p, 1), 1));
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new Prestidigitation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLK);
        }
    }
}