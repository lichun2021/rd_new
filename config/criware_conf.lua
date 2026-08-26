-- weichao   2022-08-15 15:43:51 
-- criware循环播放配置 TA维护
--[[
	使用示例
		1.直接创建
			self.mCriwareHandler = _GModel.RACriwareManager:CreateCriwareSprite(mTestVideoNode, "xxx.usm", self) -- 创建视频, 如果找不到, 报错.
			self.mCriwareHandler:loop(true) -- 不调用时, 默认为 false .
			self.mCriwareHandler:setBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA) -- 不调用时, 默认为 (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

		2.走配置
			self.mCriwareHandler = _GModel.RACriwareManager:CreateCriwareSprite(mTestVideoNode, "AAA", self) -- 创建配置, 如果找不到, 报错.
			
			配置的视频在切换下一个的时候会提前几帧.
			 
			混合方式
			src dst
				GL_ZERO                           
				GL_ONE                            
				GL_SRC_COLOR                      
				GL_ONE_MINUS_SRC_COLOR            
				GL_SRC_ALPHA                      
				GL_ONE_MINUS_SRC_ALPHA            
				GL_DST_ALPHA                      
				GL_ONE_MINUS_DST_ALPHA            
				GL_DST_COLOR                      
				GL_ONE_MINUS_DST_COLOR            
				GL_SRC_ALPHA_SATURATE             
			
			配置结构
				src 混合源
				dst 混合目标
				maxPixelSize 视频尺寸x*y
				queue 队列
				  "1.usm", a,b, c,d,  
				  "1.usm": 视频名字.
				  a,b: 随机播放次数范围.
				  c,d: 播完之后, 播放队列中哪一个范围.
]]

local criware_conf = {
    usm_Act507 = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Act507_In.usm",    	1,1,    2,2,
            "usm_Act507_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Act5001 = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Act5001_In.usm",    1,1,    2,2,
            "usm_Act5001_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Act5002 = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Act5002_In.usm",    1,1,    2,2,
            "usm_Act5002_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Act114 = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Act114_In.usm",    1,1,    2,2,
            "usm_Act114_Loop.usm",    1,1,    2,2,
        }
    },
	usm_GlorySeason_Intro_L = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_GlorySeason_Intro_L.usm",    1,1,    2,2,
            "usm_GlorySeason_Loop.usm",    1,1,    2,2,
        }
    },
	usm_GlorySeason_Intro_R = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_GlorySeason_Intro_R.usm",    1,1,    2,2,
            "usm_GlorySeason_Loop.usm",    1,1,    2,2,
        }
    },
		usm_Tiberium_Main = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Tiberium_Main_In.usm",    1,1,    2,2,
            "usm_Tiberium_Main_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeAlpha_In = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeAlpha_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeAlpha_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeAlpha_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeAlpha_Loop.usm",    1,1,    1,1,
        }
    },
	usm_Mecha_CodeBlast_In = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeBlast_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeBlast_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeBlast_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeBlast_Loop.usm",    1,1,    1,1,
        }
    },
	usm_Mecha_CodeDestroy_In = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeDestroy_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeDestroy_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeDestroy_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeDestroy_Loop.usm",    1,1,    1,1,
        }
    },
	usm_Mecha_CodeFree_In = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeFree_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeFree_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeFree_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeFree_Loop.usm",    1,1,    1,1,
        }
    },
	usm_Mecha_CodeGhost_In = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeGhost_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeGhost_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeGhost_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeGhost_Loop.usm",    1,1,    1,1,
        }
    },
	usm_Mecha_CodeNighthawk_In = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeNighthawk_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeNighthawk_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeNighthawk_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeNighthawk_Loop.usm",    1,1,    1,1,
        }
    },
	usm_Mecha_CodeRedemption_In	= {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeRedemption_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeRedemption_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeRedemption_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeRedemption_Loop.usm",    1,1,    1,1,
        }
    },
	usm_Mecha_CodeTitan_In = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeTitan_In.usm",    1,1,    2,2,
            "usm_Mecha_CodeTitan_Loop.usm",    1,1,    2,2,
        }
    },
	usm_Mecha_CodeTitan_Loop = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
		maxPixelSize = 1281000,
        queue = {
            "usm_Mecha_CodeTitan_Loop.usm",    1,1,    1,1,
        }
    },
    usm_Act5003 = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
        maxPixelSize = 1281000,
        queue = {
            "usm_Act5003_In.usm",    1,1,    2,2,
            "usm_Act5003_Loop.usm",    1,1,    2,2,
        }
    },
	
	
	
	
	
	
    seamless = {
        src = GL_SRC_ALPHA, 
        dst = GL_ONE_MINUS_SRC_ALPHA,
        queue = {
            "seamless_1.usm",    	1,1,    2,2,
            "seamless_2.usm",    1,1,    3,3,
            "seamless_3.usm",    1,1,    4,4,
            "seamless_4.usm",    1,1,    5,5,
            "seamless_5.usm",    1,1,    6,6,
            "seamless_6.usm",    1,1,    7,7,
            "seamless_7.usm",    1,1,    7,7,
        }
    },
}
return criware_conf