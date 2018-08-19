package mysticmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mysticmod.patches.AbstractCardEnum;
import mysticmod.powers.SpellsPlayed;
import mysticmod.powers.TechniquesPlayed;

import basemod.abstracts.CustomCard;

public class MirrorStrike
        extends CustomCard {
    public static final String ID = "mysticmod:MirrorStrike";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/mirrorstrike.png";
    public static final String ALTERNATE_IMG_PATH = "mysticmod/images/cards/alternate/mirrorstrike.png";
    private static final int COST = 1;
    public static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_ATK = 2;

    public MirrorStrike() {
        super(ID, NAME, ALTERNATE_IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        loadCardImage(IMG_PATH);
        this.damage=this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.DamageAction(
                        m, new DamageInfo(p, this.damage, this.damageTypeForTurn)
                        , AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (p.hasPower(SpellsPlayed.POWER_ID) && p.getPower(SpellsPlayed.POWER_ID).amount >= 2) {
            int extraAttackCount = p.getPower(SpellsPlayed.POWER_ID).amount / 2;
            for (int i = 0; i < extraAttackCount; i++) {
                AbstractDungeon.actionManager.addToBottom(
                        new com.megacrit.cardcrawl.actions.common.DamageAction(
                                m, new DamageInfo(p, this.damage, this.damageTypeForTurn)
                                , AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TechniquesPlayed(p, 1), 1));
        loadCardImage(IMG_PATH);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(SpellsPlayed.POWER_ID) && AbstractDungeon.player.getPower(SpellsPlayed.POWER_ID).amount >= 2) {
            loadCardImage(ALTERNATE_IMG_PATH);
        } else {
            loadCardImage(IMG_PATH);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MirrorStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATK);
        }
    }
}