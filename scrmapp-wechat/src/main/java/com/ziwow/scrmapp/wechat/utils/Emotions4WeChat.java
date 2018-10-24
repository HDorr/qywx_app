package com.ziwow.scrmapp.wechat.utils;

public class Emotions4WeChat
{
	public static String AgentGet(String content)
	{
		content = content.replace ( "\"[微笑]\"", "/::)" );
		content = content.replace ( "\"[撇嘴]\"", "/::~" );
		content = content.replace ( "\"[色]\"", "/::B" );
		content = content.replace ( "\"[发呆]\"", "/::|" );
		content = content.replace ( "\"[得意]\"", "/:8-)" );
		content = content.replace ( "\"[流泪]\"", "/::<" );
		content = content.replace ( "\"[害羞]\"", "/::$" );
		content = content.replace ( "\"[闭嘴]\"", "/::X" );
		content = content.replace ( "\"[睡]\"", "/::Z" );
		content = content.replace ( "\"[大哭]\"", "/::'(" );
		content = content.replace ( "\"[尴尬]\"", "/::-|" );
		content = content.replace ( "\"[发怒]\"", "/::@" );
		content = content.replace ( "\"[调皮]\"", "/::P" );
		content = content.replace ( "\"[呲牙]\"", "/::D" );
		content = content.replace ( "\"[惊讶]\"", "/::O" );
		content = content.replace ( "\"[难过]\"", "/::(" );
		content = content.replace ( "\"[酷]\"", "/::+" );
		content = content.replace ( "\"[冷汗]\"", "/:--b" );
		content = content.replace ( "\"[抓狂]\"", "/::Q" );
		content = content.replace ( "\"[吐]\"", "/::T" );
		content = content.replace ( "\"[偷笑]\"", "/:,@P" );
		content = content.replace ( "\"[愉快]\"", "/:,@-D" );
		content = content.replace ( "\"[白眼]\"", "/::d" );
		content = content.replace ( "\"[傲慢]\"", "/:,@o" );
		content = content.replace ( "\"[饥饿]\"", "/::g" );
		content = content.replace ( "\"[困]\"", "/:|-)" );
		content = content.replace ( "\"[惊恐]\"", "/::!" );
		content = content.replace ( "\"[流汗]\"", "/::L" );
		content = content.replace ( "\"[憨笑]\"", "/::>" );
		content = content.replace ( "\"[悠闲]\"", "/::,@" );
		content = content.replace ( "\"[奋斗]\"", "/:,@f" );
		content = content.replace ( "\"[咒骂]\"", "/::-S" );
		content = content.replace ( "\"[疑问]\"", "/:?" );
		content = content.replace ( "\"[嘘]\"", "/:,@x" );
		content = content.replace ( "\"[晕]\"", "/:,@@" );
		content = content.replace ( "\"[疯了]\"", "/::8" );
		content = content.replace ( "\"[衰]\"", "/:,@!" );
		content = content.replace ( "\"[骷髅]\"", "/:!!!" );
		content = content.replace ( "\"[敲打]\"", "/:xx" );
		content = content.replace ( "\"[再见]\"", "/:bye" );
		content = content.replace ( "\"[擦汗]\"", "/:wipe" );
		content = content.replace ( "\"[抠鼻]\"", "/:dig" );
		content = content.replace ( "\"[鼓掌]\"", "/:handclap" );
		content = content.replace ( "\"[糗大了]\"", "/:&-(" );
		content = content.replace ( "\"[坏笑]\"", "/:B-)" );
		content = content.replace ( "\"[左哼哼]\"", "/:<@" );
		content = content.replace ( "\"[右哼哼]\"", "/:@>" );
		content = content.replace ( "\"[哈欠]\"", "/::-O" );
		content = content.replace ( "\"[鄙视]\"", "/:>-|" );
		content = content.replace ( "\"[委屈]\"", "/:P-(" );

		return content;
	}

	public static String ClientGet(String content)
	{
		content = content.replace ( "/::)", "[微笑]" );
		content = content.replace ( "/::~", "[撇嘴]" );
		content = content.replace ( "/::B", "[色]" );
		content = content.replace ( "/::|", "[发呆]" );
		content = content.replace ( "/:8-)", "[得意]" );
		content = content.replace ( "/::<", "[流泪]" );
		content = content.replace ( "/::$", "[害羞]" );
		content = content.replace ( "/::X", "[闭嘴]" );
		content = content.replace ( "/::Z", "[睡]" );
		content = content.replace ( "/::'(", "[大哭]" );
		content = content.replace ( "/::-|", "[尴尬]" );
		content = content.replace ( "/::@", "[发怒]" );
		content = content.replace ( "/::P", "[调皮]" );
		content = content.replace ( "/::D", "[呲牙]" );
		content = content.replace ( "/::O", "[惊讶]" );
		content = content.replace ( "/::(", "[难过]" );
		content = content.replace ( "/::+", "[酷]" );
		content = content.replace ( "/:--b", "[冷汗]" );
		content = content.replace ( "/::Q", "[抓狂]" );
		content = content.replace ( "/::T", "[吐]" );
		content = content.replace ( "/:,@P", "[偷笑]" );
		content = content.replace ( "/:,@-D", "[愉快]" );
		content = content.replace ( "/::d", "[白眼]" );
		content = content.replace ( "/:,@o", "[傲慢]" );
		content = content.replace ( "/::g", "[饥饿]" );
		content = content.replace ( "/:|-)", "[困]" );
		content = content.replace ( "/::!", "[惊恐]" );
		content = content.replace ( "/::L", "[流汗]" );
		content = content.replace ( "/::>", "[憨笑]" );
		content = content.replace ( "/::,@", "[悠闲]" );
		content = content.replace ( "/:,@f", "[奋斗]" );
		content = content.replace ( "/::-S", "[咒骂]" );
		content = content.replace ( "/:?", "[疑问]" );
		content = content.replace ( "/:,@x", "[嘘]" );
		content = content.replace ( "/:,@@", "[晕]" );
		content = content.replace ( "/::8", "[疯了]" );
		content = content.replace ( "/:,@!", "[衰]" );
		content = content.replace ( "/:!!!", "[骷髅]" );
		content = content.replace ( "/:xx", "[敲打]" );
		content = content.replace ( "/:bye", "[再见]" );
		content = content.replace ( "/:wipe", "[擦汗]" );
		content = content.replace ( "/:dig", "[抠鼻]" );
		content = content.replace ( "/:handclap", "[鼓掌]" );
		content = content.replace ( "/:&-(", "[糗大了]" );
		content = content.replace ( "/:B-)", "[坏笑]" );
		content = content.replace ( "/:<@", "[左哼哼]" );
		content = content.replace ( "/:@>", "[右哼哼]" );
		content = content.replace ( "/::-O", "[哈欠]" );
		content = content.replace ( "/:>-|", "[鄙视]" );
		content = content.replace ( "/:P-(", "[委屈]" );

		return content;
	}
}
