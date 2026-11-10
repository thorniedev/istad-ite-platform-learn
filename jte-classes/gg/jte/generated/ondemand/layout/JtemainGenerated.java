package gg.jte.generated.ondemand.layout;
public final class JtemainGenerated {
	public static final String JTE_NAME = "layout/main.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,5,5,5,5,5,5,5,5,5,5,9,9,9,17,17,17,17,18,18,18,18,24,24,24,31,31,31,0,1,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, gg.jte.Content content, String lang) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html");
		var __jte_html_attribute_0 = lang;
		if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
			jteOutput.writeContent(" lang=\"");
			jteOutput.setContext("html", "lang");
			jteOutput.writeUserContent(__jte_html_attribute_0);
			jteOutput.setContext("html", null);
			jteOutput.writeContent("\"");
		}
		jteOutput.writeContent(">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(title);
		jteOutput.writeContent(" - ISTAD IAM</title>\n    <script src=\"https://cdn.tailwindcss.com\"></script>\n</head>\n<body class=\"bg-slate-50 text-slate-900 min-h-screen flex flex-col justify-between\">\n    <header class=\"bg-white border-b border-slate-200 px-6 py-4 flex justify-between items-center shadow-sm\">\n        <a href=\"/\" class=\"text-xl font-bold text-indigo-600\">ISTAD Identity</a>\n        <div class=\"flex items-center space-x-4\">\n            <div class=\"text-sm\">\n                <a href=\"?lang=en\" class=\"px-2 py-1 rounded ");
		jteOutput.setContext("a", "class");
		jteOutput.writeUserContent("en".equals(lang) ? "font-bold underline text-indigo-600" : "text-slate-500");
		jteOutput.setContext("a", null);
		jteOutput.writeContent("\">English</a> |\n                <a href=\"?lang=km\" class=\"px-2 py-1 rounded ");
		jteOutput.setContext("a", "class");
		jteOutput.writeUserContent("km".equals(lang) ? "font-bold underline text-indigo-600" : "text-slate-500");
		jteOutput.setContext("a", null);
		jteOutput.writeContent("\">ខ្មែរ</a>\n            </div>\n        </div>\n    </header>\n\n    <main class=\"flex-grow flex items-center justify-center p-6\">\n        ");
		jteOutput.setContext("main", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n    </main>\n\n    <footer class=\"text-center py-4 text-xs text-slate-400 border-t border-slate-200\">\n        &copy; 2026 Institute of Science and Technology Advanced Development (ISTAD)\n    </footer>\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.get("title");
		gg.jte.Content content = (gg.jte.Content)params.get("content");
		String lang = (String)params.getOrDefault("lang", "en");
		render(jteOutput, jteHtmlInterceptor, title, content, lang);
	}
}
