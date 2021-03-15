<!--  -->
<template>
	<div class="page">
		<div class="mainHead">
			<h3>泉州概况</h3>
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
				<el-breadcrumb-item>泉州概况</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="mainBody">
			<div class="left">
				<img src="../assets/images/qzsi.jpg" alt />
				<div class="btn">
					<el-button round @click="change1()">泉州宣传片</el-button>
					<el-button round @click="change2()">基本概况</el-button>
					<el-button round @click="change3()">县市区概况</el-button>
				</div>
			</div>
			<div class="right">
				<!-- 宣传片 -->
				<div class="qzvideo" v-show="qzvideoVis">
					<video-player
						class="vjs-custom-skin"
						ref="videoPlayer"
						:options="playerOptions"
						:playsinline="true"
					></video-player>
					<p>视频来源：http://www.quanzhou.gov.cn/zfb/zjqz/qzgk/</p>
				</div>
				<!-- 基本概况 -->
				<div class="qzsituation" v-show="qzsiVis">
					<div class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
						<p>
							泉州市地处福建省东南部，是福建省三大中心城市之一，北承省
							会福州，南接厦门特区，东望台湾宝岛，西毗漳州、龙岩、三明。
							现辖鲤城、丰泽、洛江、泉港4个区，晋江、石狮、南安3个县级市，
							惠安、安溪、永春、德化、金门（待统一）5个县和泉州经济技术
							开发区、泉州台商投资区。全市土地面积11015平方公里（含金门），
							2018年末常住人口870万人（不含金门县）。根据2010年第六次全
							国人口普查数据，少数民族有53个，以回族、土家族、苗族和畲族居
							多。方言以闽南话为主，通用语言为普通话。
						</p>
						<span>
							<b>地势</b>
						</span>
						<p>
							泉州依山面海，境内山峦起伏，丘陵、河谷、盆地错落其间，地
							势西北高东南低，山地1000多万亩，山地、丘陵占土地总面积的
							五分之四，俗称“八山一水一分田”。
						</p>
						<span>
							<b>海岸港口</b>
						</span>
						<p>
							海域面积11360平方公里，海岸线总长541公里，大小港湾14个，岛
							屿207个。深水良港多，可建万吨以上深水泊位123个，湄洲湾南岸的
							肖厝港和斗尾港是世界不多、中国少有的天然良港。可作业的海洋渔
							场面积5000多平方公里，可供开发利用面积118平方公里，主要水产
							生物500多种，主要经济鱼类近百种，盛产牡蛎、蛏、蛤、螺、海带、
							紫菜等贝、藻类200多种。
						</p>
						<span>
							<b>气候</b>
						</span>
						<p>
							泉州属亚热带海洋性季风气候，年平均气温19.5℃～22℃，终年温
							暖湿润，四季如春，古诗称泉州“四季有花常见雨，一冬无雪却闻雷”
							，故泉州有“温陵”之雅称。
						</p>
						<span>
							<b>资源</b>
						</span>
						<p>
							全市森林蓄积量4017.74万立方米。泉州现已探明或基本探明有一定
							地质储量的矿产地130多处，其中大型矿产地8处，主要矿产资源30多种，
							有高岭土、花岗岩、辉绿岩、石英砂、石灰石、煤、铁、锰、地热、矿
							泉水等30多种。全市常年雨量充沛，境内溪流多达34条，总长1620公里，
							地表水总量87.6亿立方米，可利用水量14.56亿立方米，水电蕴蓄量达
							43万千瓦。晋江为泉州市第一大河，发源于戴云山东南麓，流经永春、
							安溪、南安、鲤城、晋江注入泉州湾，水系河流全长404.8公里，流域面
							积5629平方公里。此外，泉州地下水资源有10亿立方米，可利用的地下水
							0.76亿立方米。
						</p>
						<span>
							<b>人文历史</b>
						</span>
						<p>
							泉州历史悠久，经济开发早在周秦时期就已开始。三国吴永安三年
							（260年），在今南安市丰州镇置东安县治，南朝梁天监间（502～519年）
							置南安郡作郡治，为本地设置县、郡治之始。西晋末年，中原战乱，中原
							士族大批入泉多沿江而居，晋江由此得名。他们带来先进的生产工艺和文
							化知识，使晋江两岸得到迅速开发。随着经济的发展和政治制度的变革，
							行政区划建制几度变迁。唐久视元年（700年）置武荣州，州治设今市区。
							唐景云二年（711年）武荣州改名泉州。中华人民共和国成立后设行政督
							察区、专区、地区，1986年1月撤晋江地区设泉州地级市。
						</p>
						<p>
							泉州是国务院首批历史文化名城、东亚文化之都、古代“海上丝绸之路”起
							点城市，文化积淀深厚，素有“海滨邹鲁”、“世界宗教博物馆”、“光明之城”
							的美誉。全市现拥有各级文物保护单位824处，其中国家级31处、省级87处。
							其中极负盛名的有中国现存最早的伊斯兰教清真寺、世界惟一的摩尼光佛像
							石刻、中国最大的老君石刻造像、葬有唐代到泉州传教的穆罕默德门徒三贤
							四贤的灵山圣墓、千年古刹开元寺及东西塔、记载古代海上交通和贸易的九
							日山祈风崖刻、广受台胞和海外侨胞信众膜拜的天后宫妈祖、民族英雄郑成
							功史迹与陵墓、宋代蔡襄修建的洛阳桥、号称“天下无桥长此桥”的安平桥，
							以及与惠东民俗、海滨风光、石雕艺术融为一体的崇武古城等。丰富的人文
							景观、别具一格的民俗风情和秀出东南的自然风光交相辉映，使泉州成为一
							个全国乃至国际性的旅游胜地，吸引着越来越多的国内外游客到此观光。泉
							州保留着弥足珍贵的戏曲文化遗产，有梨园戏、高甲戏、打城戏、“嘉礼”戏
							等剧种，其中蜚声海内外的有：晋唐士乐余韵南音、宋元南戏“活化石”梨园
							戏和中国一绝“提线木偶”。泉州作为古代“海上丝绸之路”的起点城市，伊斯
							兰教、印度教、古基督教、摩尼教、犹太教、佛教等世界多种宗教在泉州广
							泛传播，留下大量遗迹，使泉州成为多元文化融洽交汇、和平共荣的载体。
							作为南少林武术的发源地，泉州具有薪传不息、独树一帜的武术文化。
						</p>
						<p>
							泉州是中国历史上对外通商的重要港口，有着上千年的海外交通史，是一座历史悠久、
							风光秀丽的开放港口城市。自唐代开始，即为中国南方四大对外通商口岸之一。宋元时期，
							泉州港跃居为四大港之首，以“刺桐港”之名驰誉世界，成为与埃及亚历山大港媲美的“东方
							第一大港”，呈现“市井十洲人”、“涨海声中万国商”的繁荣景象。
						</p>
						<p>
							泉州是全国著名侨乡和台湾汉族同胞主要祖籍地。旅居世界各地的泉州籍华侨、
							华人720多万人，港澳同胞70多万人，台湾汉族同胞约900多万人。长期以来，旅外乡
							亲热心桑梓建设，或投资办厂，或兴学育人，或捐资慈善，或兴办公益，涌现出一大
							批贡献巨大、成就杰出的乡贤硕彦，成为推动泉州经济社会发展的重要力量，成为泉
							州与世界密切联系的纽带和桥梁。
						</p>
						<span>
							<b>经济发展</b>
						</span>
						<p>
							改革开放以前，由于泉州地处海防前线，国家投资少，经济长期处于以农业
							为主的自给、半自给状态，经济总量居全省地市倒数第二。改革开放以来，
							泉州人民高举邓小平理论伟大旗帜，坚持以“三个代表”重要思想为指导，抓
							住机遇、扭住中心、爱拼敢赢、大胆实践，经历“三来一补”起步、乡镇企业
							铺路、三资企业上路、成片开发迈大步、区城经济展宏图的经济发展阶段，
							走出一条“以市场化为制度基础、民营经济与外向型经济互相促进为最大特色、
							县域经济发达为突出亮点、品牌化为突出优势”的具有侨乡特色的经济建设路子，
							创造了“泉州模式”。经济实力隔几年上一个台阶，成为福建省乃至全国发展最快、
							最具活力的地区之一，创造泉州发展历史的新辉煌，也成为全国18个改革开放典
							型地区之一。全市GDP在1978年为7.79亿元，1992年突破100亿元，2002年突破
							1000亿元，2013年突破5000亿元，2018年达8467.98亿元；人均地区生产总值由
							1978年的171元增至97614元；一般公共预算总收入由0.8亿元增至861.05亿元；
							1978年农民人均纯收入仅为75元，2018年农村居民人均可支配收入达20277元；
							城镇居民人均可支配收入由1978年的324元增至46111元。
						</p>
						<p>
							泉州人民立足实际，奋力打造特色经济，促进泉港“石化基地”、丰泽“中国树脂工艺之乡”、
							晋江“中国鞋都”、石狮“中国服装名城”、南安 “中国建材之乡”、惠安 “中国石雕之乡”、德化
							“工艺陶瓷之乡”、永春“芦柑之乡”、安溪“乌龙茶之乡”等特色经济的形成并驰名海内外。
						</p>
						<p>
							专业化、基地化、区域化、集群化的生产加快城乡一体化进程，带动大泉州城市建设与繁荣，
							城乡面貌发生历史性巨变。全市城镇化水平达65.7%，环湾区域建成区面积220平方公里，中心市区
							绿化覆盖率43.2%，一个以中心城市为核心，沿海次中心城市为骨干，立体交通网络完备、市政设施
							完善，具有闽南侨乡特色的泉州湾都市群雏形初现，泉州以组团式、海湾型的城市形态进入全省三
							大中心城市行列。
						</p>
						<p>
							在推进经济建设的同时，抓好文化和城市建设，荣膺国际花园城市、全国文明城市、
							最佳中国魅力城市、国家卫生城市、国家环保模范城市、国家生态市、全国绿化模范城市、
							全国文化模范城市、中国优秀旅游城市、国家园林城市、中国优秀创新型城市、中国品牌
							经济城市、水环境治理优秀范例城市、中国十大和谐城市、中国人居环境奖、市民最满意
							城市、感动世界的中国品牌城市、全国综治最高奖“长安杯”获奖城市等称号和荣誉。
						</p>
					</div>
				</div>
				<!-- 县市区概况 -->
				<div class="qzcounty" v-show="qzcountyVis">
					<div>
						<ul>
							<li>
								<a href="http://www.qzlc.gov.cn/zjlc/">鲤城区</a>
							</li>
							<li>
								<a href="http://www.qzfz.gov.cn/zjfz/fzgk/">丰泽区</a>
							</li>
							<li>
								<a href="http://www.qzlj.gov.cn/zjlj/ljgk/201905/t20190527_1676581.htm">洛江区</a>
							</li>
							<li>
								<a href="http://www.qg.gov.cn/zjqg/">泉港区</a>
							</li>
							<li>
								<a href="http://www.jinjiang.gov.cn/zjjj/jjgk/201208/t20120820_904599.htm">晋江市</a>
							</li>
							<li>
								<a href="http://www.shishi.gov.cn/zjss/">石狮市</a>
							</li>
							<li>
								<a href="http://www.nanan.gov.cn/zjna/nagk/">南安市</a>
							</li>
							<li>
								<a href="http://www.huian.gov.cn/zjha.aspx">惠安县</a>
							</li>
							<li>
								<a href="http://www.fjax.gov.cn/zjax/axgl/">安溪县</a>
							</li>
							<li>
								<a href="http://www.fjyc.gov.cn/zjyc/ycgk/">永春县</a>
							</li>
							<li>
								<a href="http://www.dehua.gov.cn/list-13.html">德化县</a>
							</li>
							<li>
								<a href="http://www.qzkfq.gov.cn/zjkfq/yqjs/yqgs/201902/t20190228_1836807.htm">州经济技术开发区</a>
							</li>
							<li>
								<a href="http://www.qzts.gov.cn/zjtstzq/qztstzqgk/">泉州台商投资区</a>
							</li>
							<li>
								<a href="http://www.quanzhou.gov.cn/zfb/zjqz/xsqgk/201709/t20170906_527728.htm">金门县（待统一）</a>
							</li>
						</ul>
					</div>
					<div class="img">
						<img src="../assets/images/qz_zjqz_pic4.png" alt />
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import '../assets/css/video.css'
export default {
	data() {
		return {
			qzvideoVis: true,
			qzsiVis: false,
			qzcountyVis: false,
			count: 0,
			// 视频播放
			playerOptions: {
				height: '360',
				autoplay: true,
				muted: true,
				language: 'en',
				playbackRates: [0.7, 1.0, 1.5, 2.0],
				sources: [
					{
						type: 'video/mp4',
						// mp4
						src: 'https://qz-1300435011.cos.ap-guangzhou.myqcloud.com/cnqz.mp4'
					}
				],
				poster: ''
			}
		}
	},
	mounted() {},
	methods: {
		change1() {
			this.qzvideoVis = true
			this.qzsiVis = false
			this.qzcountyVis = false
		},
		change2() {
			this.qzvideoVis = false
			this.qzsiVis = true
			this.qzcountyVis = false
		},
		change3() {
			this.qzvideoVis = false
			this.qzsiVis = false
			this.qzcountyVis = true
		},
		load() {
			this.count += 3
		}
	}
}
</script>
<style lang='stylus' scoped>
.page
	padding 0 8.125rem
	.mainHead
		height 3.75rem
		border-bottom 0.0625rem #ccc solid
		// overflow hidden
		display flex
		justify-content space-between
		h3
			margin 0
			width 10rem
			// height 3.625rem
			border-bottom 0.125rem #117750 solid
			font-size 1.5rem
			color #117750
			line-height 3.625rem
			padding 0 1.25rem 0 2.5rem
		.el-breadcrumb
			font-size .9375rem
			line-height 3.75rem
	.mainBody
		margin 3.125rem 1.875rem
		display flex
		// justify-content center
		.left
			.btn
				margin .625rem 0
				display flex
				flex-direction column
				.el-button
					margin .625rem 0 .3125rem 0
					background-color #db0102
					color #fff
		.right
			margin-left 1.25rem
			.qzvideo
				margin 1.25rem 10rem 0 5rem
				height 32.25rem
				width 54.125rem
				p
					margin-top .3125rem
			.qzsituation
				.infinite-list
					height 90%
					line-height 1.8rem
					font-size .9375rem
					padding .9375rem 0 .9375rem 3.125rem
					span
						font-size 1.125rem
						margin-top .625rem
						margin-left 2em
					p
						text-indent 2em
						margin-top .3125rem
			.qzcounty
				display flex
				div
					margin-top .625rem
					ul
						li
							background url('../assets/images/jiantou.png') no-repeat left center
							line-height 1.875rem
							height 1.875rem
							padding-left 1.875rem
							a
								color #000
				.img
					margin 1.25rem 5rem 1.25rem 5rem
@media only screen and (min-width: 20rem) and (max-width: 48rem)
	.page
		padding 0 2.125rem
		.mainHead
			display flex
			justify-content space-between
			.el-breadcrumb
				font-size .9375rem
				line-height 3.75rem
		.mainBody
			margin 1rem
			display flex
			flex-direction column
			.left
				img
					display none
				.btn
					margin .625rem 0
					display flex
					flex-direction row
					.el-button
						margin .625rem .3125rem
						font-size 1rem
			.right
				margin 0
				.qzvideo
					margin 0
					height 15.75rem
					width 100%
					p
						margin-top .3125rem
				.qzsituation
					.infinite-list
						font-size 1.25rem
						padding 0
				.qzcounty
					display flex
					div
						margin-top .625rem
						ul
							padding 0
							li
								width	100%
								background url('../assets/images/jiantou.png') no-repeat left center
								line-height 1.875rem
								height 1.875rem
								padding-left 1.875rem
								a
									color #000
					.img
						margin 1.25rem 5rem 1.25rem 5rem
						img
							height 100%
							width 100%
</style>
