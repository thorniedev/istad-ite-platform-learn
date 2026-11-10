package gg.jte.generated.ondemand;
public final class JteloginGenerated {
	public static final String JTE_NAME = "login.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,4,4,8,8,10,10,10,12,12,14,14,14,16,16,28,28,28,47,47,47,50,50,50,50,50,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String error, String lang, java.util.function.Function<String, String> msg) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainGenerated.render(jteOutput, jteHtmlInterceptor, msg.apply("login.title"), new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div class=\"w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8\">\n            <h1 class=\"text-2xl font-bold text-center text-slate-800 mb-6\">");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(msg.apply("login.title"));
				jteOutput.writeContent("</h1>\n\n            ");
				if (error != null) {
					jteOutput.writeContent("\n                <div class=\"mb-4 p-3 bg-red-50 border border-red-200 text-red-600 rounded text-sm\">\n                    ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(error);
					jteOutput.writeContent("\n                </div>\n            ");
				}
				jteOutput.writeContent("\n\n            <form method=\"post\" action=\"/login\" class=\"space-y-4\">\n                <div>\n                    <label class=\"block text-sm font-medium text-slate-700 mb-1\">Username or Email</label>\n                    <input type=\"text\" name=\"username\" required autofocus class=\"w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none\">\n                </div>\n                <div>\n                    <label class=\"block text-sm font-medium text-slate-700 mb-1\">Password</label>\n                    <input type=\"password\" name=\"password\" required class=\"w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none\">\n                </div>\n                <button type=\"submit\" class=\"w-full py-2 px-4 bg-indigo-600 hover:bg-indigo-700 text-white font-medium rounded-lg shadow transition\">\n                    ");
				jteOutput.setContext("button", null);
				jteOutput.writeUserContent(msg.apply("login.title"));
				jteOutput.writeContent("\n                </button>\n            </form>\n\n            <div class=\"mt-6\">\n                <div class=\"relative\">\n                    <div class=\"absolute inset-0 flex items-center\"><div class=\"w-full border-t border-slate-200\"></div></div>\n                    <div class=\"relative flex justify-center text-xs uppercase\"><span class=\"bg-white px-2 text-slate-400\">Or continue with</span></div>\n                </div>\n\n                <div class=\"grid grid-cols-2 gap-3 mt-4\">\n                    <a href=\"/oauth2/authorization/google\" class=\"flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium\">Google</a>\n                    <a href=\"/oauth2/authorization/github\" class=\"flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium\">GitHub</a>\n                    <a href=\"/oauth2/authorization/facebook\" class=\"flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium\">Facebook</a>\n                    <a href=\"/oauth2/authorization/telegram\" class=\"flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium\">Telegram</a>\n                </div>\n            </div>\n\n            <p class=\"mt-6 text-center text-sm text-slate-500\">\n                Don't have an account? <a href=\"/register\" class=\"text-indigo-600 font-medium hover:underline\">");
				jteOutput.setContext("a", null);
				jteOutput.writeUserContent(msg.apply("register.title"));
				jteOutput.writeContent("</a>\n            </p>\n        </div>\n    ");
			}
		}, lang);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String error = (String)params.getOrDefault("error", null);
		String lang = (String)params.getOrDefault("lang", "en");
		java.util.function.Function<String, String> msg = (java.util.function.Function<String, String>)params.get("msg");
		render(jteOutput, jteHtmlInterceptor, error, lang, msg);
	}
}
