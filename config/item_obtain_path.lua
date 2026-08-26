local item_obtain_path = {
	speed_up = {
        title = '@SpeedUpEarnWay',
        desc = '@SpeedUpEarnWayDes',
        items = {
            {
                icon = 'Item_BuyInShop.png',
                desc = '@BuyInShop',
                page = 'RARechargeMainPage',
                data = {
                    tabIndex = _GModel.RAStoreManager.STORETAB.HOT,
                    subIndex = _GModel.RAStoreManager.STORE_HOT_TAB.SPEED
                },
            },
            {
                icon = 'Item_BuyInAllianceShop.png',
                desc = '@BuyInAllianceShop',
                page = 'RAAllianceShopPage',
            },
            {
                icon = 'ItemSourceIcon_06.png',
                desc = '@itemGetDesc11',
                gotoType = 200,
            },
        },
    },
    army_add = {
        title = '@ArmyAddWay',
        desc = '@ArmyAddWayWayDes',
        items = {
            {
                icon = 'Item_BuyInShop.png',
                desc = '@ArmyAddTank',
                gotoType = 1125,
                gotoParam = _GModel.Const_pb.WAR_FACTORY,
            },
            {
                icon = 'Item_BuyInShop.png',
                desc = '@ArmyAddSoldier',
                gotoType = 1125,
                gotoParam = _GModel.Const_pb.BARRACKS,
            },
            {
                icon = 'Item_BuyInShop.png',
                desc = '@ArmyAddAirForce',
                gotoType = 1125,
                gotoParam = _GModel.Const_pb.AIR_FORCE_COMMAND,
            },
            {
                icon = 'Item_BuyInShop.png',
                desc = '@ArmyAddRemote',
                gotoType = 1125,
                gotoParam = _GModel.Const_pb.REMOTE_FIRE_FACTORY,
            },
        },
    },
    res_add = {
        title = '@ResAddWay',
        desc = '@ResAddWayWayDes',
        items = {            
            {
                icon = 'Item_BuyInShop.png',
                desc = '@ResAddGotoShowRes',
                gotoType = 209,
            },
            {
                icon = 'Item_BuyInShop.png',
                desc = '@ResAddGotoCollect',
                gotoType = 31,
                gotoParam = '1_1_1007',
            },
            {
                icon = 'Item_BuyInShop.png',
                desc = '@ResAddBuyInShop',
                page = 'RARechargeMainPage',
                data = {
                    tabIndex = _GModel.RAStoreManager.STORETAB.HOT,
                    subIndex = _GModel.RAStoreManager.STORE_HOT_TAB.RES
                },
            },
        },
    },
    material = {
        title = '@GetWaysTitle',
        desc = '@GetWaysDescription',
        items = {
            {
                icon = 'HUD_EquipMat.png',
                desc = '@GetWays',
                page = 'RAEquipMatOutputPage',
            },
        },
    },
    ns_soldier_recover = {    -- 国家医院死兵加速
        title = '@NSSoldierRecoverTitle',
        desc = '@NSSoldierRecoverDes',
        items = {
            {
                icon = "Activity_Icon_90_Sel.png",
                desc = "@NSSoldierRecoverWay",
                noBtn = true,
            },
        },
    },
}
return item_obtain_path
