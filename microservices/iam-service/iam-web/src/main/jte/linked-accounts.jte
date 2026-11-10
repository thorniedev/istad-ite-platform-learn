@param java.util.List<String> providers
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = msg.apply("profile.linkedAccounts"),
    lang = lang,
    content = @`
        <div class="w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8">
            <h1 class="text-xl font-bold text-slate-800 mb-6">${msg.apply("profile.linkedAccounts")}</h1>
            <div class="space-y-3">
                @for(String p : providers)
                    <div class="flex justify-between items-center p-3 bg-slate-50 rounded-lg border">
                        <span class="font-medium text-sm text-slate-700">${p}</span>
                        <span class="text-xs text-emerald-600 font-semibold">&#10003; Connected</span>
                    </div>
                @endfor
            </div>
            <div class="pt-6 border-t mt-6">
                <a href="/" class="text-sm text-indigo-600 hover:underline">&larr; Back to profile</a>
            </div>
        </div>
    `
)