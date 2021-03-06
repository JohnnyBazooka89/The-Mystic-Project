package mysticmod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.patches.MysticTags;

public class Grapple extends AbstractMysticCard {
    public static final String ID = "mysticmod:Grapple";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/grapple.png";
    private static final int COST = 1;

    public Grapple() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        exhaust = true;
        isEthereal = true;
        tags.add(MysticTags.IS_ARTE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, p));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EntanglePower(p)));
    }

    @Override
    public boolean hasEnoughEnergy() {
        boolean returnValue = super.hasEnoughEnergy();
        for (AbstractCard playedCard : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (playedCard.type == AbstractCard.CardType.ATTACK) {
                return false;
            }
        }
        return returnValue;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Grapple();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
