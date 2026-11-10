@param String username
@param String fullName
@param java.util.Set<String> roles
@param java.util.Set<String> permissions
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = msg.apply("profile.title"),
    lang = lang,
    content = @`
        <div class="w-full max-w-2xl bg-white rounded-xl shadow-md border border-slate-200 p-8 space-y-6">
            <div class="flex justify-between items-center border-b pb-4">
                <div>
                    <h1 class="text-2xl font-bold text-slate-800">${fullName}</h1>
                    <p class="text-sm text-slate-500">@${username}</p>
                </div>
                <a href="/logout" class="py-1 px-3 border border-red-200 text-red-600 hover:bg-red-50 text-sm rounded-lg">
                    ${msg.apply("common.logout")}
                </a>
            </div>

            <div>
                <h2 class="text-sm font-semibold uppercase text-slate-400 mb-2">Assigned Roles</h2>
                <div class="flex flex-wrap gap-2">
                    @for(String role : roles)
                        <span class="px-3 py-1 bg-indigo-50 text-indigo-700 border border-indigo-200 rounded-full text-xs font-semibold">${role}</span>
                    @endfor
                </div>
            </div>

            <div>
                <h2 class="text-sm font-semibold uppercase text-slate-400 mb-2">Effective Permissions</h2>
                <div class="flex flex-wrap gap-2">
                    @for(String perm : permissions)
                        <span class="px-2 py-1 bg-slate-100 text-slate-700 rounded text-xs font-mono">${perm}</span>
                    @endfor
                </div>
            </div>

            <div class="pt-4 border-t flex space-x-4 text-sm font-medium">
                <a href="/profile/edit" class="text-indigo-600 hover:underline">Edit Profile</a>
                <a href="/profile/password" class="text-indigo-600 hover:underline">Change Password</a>
                <a href="/profile/accounts" class="text-indigo-600 hover:underline">Linked Accounts</a>
                <a href="/profile/apps" class="text-indigo-600 hover:underline">Connected Apps</a>
            </div>
        </div>
    `
)