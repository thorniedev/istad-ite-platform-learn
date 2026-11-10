@param String error = null
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = msg.apply("register.title"),
    lang = lang,
    content = @`
        <div class="w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8">
            <h1 class="text-2xl font-bold text-center text-slate-800 mb-6">${msg.apply("register.title")}</h1>

            @if(error != null)
                <div class="mb-4 p-3 bg-red-50 border border-red-200 text-red-600 rounded text-sm">
                    ${error}
                </div>
            @endif

            <form method="post" action="/register" class="space-y-4">
                <div class="grid grid-cols-2 gap-3">
                    <div>
                        <label class="block text-sm font-medium text-slate-700 mb-1">First Name</label>
                        <input type="text" name="givenName" required class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500">
                    </div>
                    <div>
                        <label class="block text-sm font-medium text-slate-700 mb-1">Last Name</label>
                        <input type="text" name="familyName" required class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500">
                    </div>
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Username</label>
                    <input type="text" name="username" required class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Email</label>
                    <input type="email" name="email" required class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Password</label>
                    <input type="password" name="password" required class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Confirm Password</label>
                    <input type="password" name="confirmPassword" required class="w-full px-3 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-indigo-500">
                </div>
                <button type="submit" class="w-full py-2 px-4 bg-indigo-600 hover:bg-indigo-700 text-white font-medium rounded-lg shadow transition">
                    ${msg.apply("register.title")}
                </button>
            </form>

            <p class="mt-6 text-center text-sm text-slate-500">
                Already have an account? <a href="/login" class="text-indigo-600 font-medium hover:underline">${msg.apply("login.title")}</a>
            </p>
        </div>
    `
)