@param String clientId
@param String clientName
@param java.util.Set<String> scopes
@param String state
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = "Consent Request",
    lang = lang,
    content = @`
        <div class="w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8">
            <h1 class="text-xl font-bold text-center text-slate-800 mb-2">Authorize Application</h1>
            <p class="text-sm text-center text-slate-500 mb-6">
                <span class="font-semibold text-indigo-600">${clientName}</span> is requesting access to your account.
            </p>

            <form method="post" action="/oauth2/authorize">
                <input type="hidden" name="client_id" value="${clientId}">
                <input type="hidden" name="state" value="${state}">

                <div class="mb-6 space-y-2 bg-slate-50 p-4 rounded-lg border border-slate-200">
                    <p class="text-xs font-semibold text-slate-400 uppercase">Permissions Requested:</p>
                    @for(String scope : scopes)
                        <div class="flex items-center text-sm text-slate-700">
                            <span class="mr-2 text-indigo-500">&#10003;</span>
                            <span class="font-mono text-xs">${scope}</span>
                        </div>
                    @endfor
                </div>

                <div class="grid grid-cols-2 gap-3">
                    <button type="submit" name="consent_action" value="cancel" class="py-2 px-4 border border-slate-300 hover:bg-slate-50 text-slate-700 font-medium rounded-lg">
                        ${msg.apply("consent.cancel")}
                    </button>
                    <button type="submit" name="consent_action" value="approve" class="py-2 px-4 bg-indigo-600 hover:bg-indigo-700 text-white font-medium rounded-lg">
                        ${msg.apply("consent.allow")}
                    </button>
                </div>
            </form>
        </div>
    `
)