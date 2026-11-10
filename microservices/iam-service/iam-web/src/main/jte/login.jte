@param String error = null
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = msg.apply("login.title"),
    lang = lang,
    content = @`
        <div class="w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8">
            <h1 class="text-2xl font-bold text-center text-slate-800 mb-6">${msg.apply("login.title")}</h1>

            @if(error != null)
                <div class="mb-4 p-3 bg-red-50 border border-red-200 text-red-600 rounded text-sm">
                    ${error}
                </div>
            @endif

            <form method="post" action="/login" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Username or Email</label>
                    <input type="text" name="username" required autofocus class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Password</label>
                    <input type="password" name="password" required class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:outline-none">
                </div>
                <button type="submit" class="w-full py-2 px-4 bg-indigo-600 hover:bg-indigo-700 text-white font-medium rounded-lg shadow transition">
                    ${msg.apply("login.title")}
                </button>
            </form>

            <div class="mt-6">
                <div class="relative">
                    <div class="absolute inset-0 flex items-center"><div class="w-full border-t border-slate-200"></div></div>
                    <div class="relative flex justify-center text-xs uppercase"><span class="bg-white px-2 text-slate-400">Or continue with</span></div>
                </div>

                <div class="grid grid-cols-2 gap-3 mt-4">
                    <a href="/oauth2/authorization/google" class="flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium">Google</a>
                    <a href="/oauth2/authorization/github" class="flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium">GitHub</a>
                    <a href="/oauth2/authorization/facebook" class="flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium">Facebook</a>
                    <a href="/oauth2/authorization/telegram" class="flex justify-center items-center py-2 px-3 border border-slate-300 rounded-lg hover:bg-slate-50 text-sm font-medium">Telegram</a>
                </div>
            </div>

            <p class="mt-6 text-center text-sm text-slate-500">
                Don't have an account? <a href="/register" class="text-indigo-600 font-medium hover:underline">${msg.apply("register.title")}</a>
            </p>
        </div>
    `
)