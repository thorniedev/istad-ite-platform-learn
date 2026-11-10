@param java.util.List<String> apps
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = "Connected Applications",
    lang = lang,
    content = @`
        <div class="w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8">
            <h1 class="text-xl font-bold text-slate-800 mb-6">Authorized Applications</h1>
            <div class="space-y-3">
                @if(apps.isEmpty())
                    <p class="text-sm text-slate-500">No third-party apps connected.</p>
                @else
                    @for(String app : apps)
                        <div class="flex justify-between items-center p-3 bg-slate-50 rounded-lg border">
                            <span class="text-sm font-medium text-slate-700">${app}</span>
                            <button class="text-xs text-red-600 hover:underline">Revoke</button>
                        </div>
                    @endfor
                @endif
            </div>
            <div class="pt-6 border-t mt-6">
                <a href="/" class="text-sm text-indigo-600 hover:underline">&larr; Back to profile</a>
            </div>
        </div>
    `
)