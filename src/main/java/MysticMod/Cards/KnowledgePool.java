package MysticMod.Cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import MysticMod.Patches.AbstractCardEnum;
import MysticMod.Actions.SpellDiscovery;

import basemod.abstracts.CustomCard;

public class KnowledgePool
        extends CustomCard {
    public static final String ID = "MysticMod:KnowledgePool";
    public static final String NAME = "Knowledge Pool";
    public static final String DESCRIPTION = "Choose one of 3 random Spell cards and add it to your hand. it costs 0 this turn. NL Exhaust.";
    public static final String IMG_PATH = "MysticMod/images/cards/knowledgepool.png";
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;


    public KnowledgePool() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.MYSTIC_PURPLE,
                AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SpellDiscovery());
        }

    @Override
    public AbstractCard makeCopy() {
        return new KnowledgePool();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
        }
    }
}