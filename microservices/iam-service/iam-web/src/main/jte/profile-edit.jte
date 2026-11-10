@param String givenName
@param String familyName
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = "Edit Profile",
    lang = lang,
    content = @`
        <div class="w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8">
            <h1 class="text-xl font-bold text-slate-800 mb-6">Edit Profile</h1>
            <form method="post" action="/profile/edit" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">First Name</label>
                    <input type="text" name="givenName" value="${givenName}" required class="w-full px-3 py-2 border rounded-lg">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Last Name</label>
                    <input type="text" name="familyName" value="${familyName}" required class="w-full px-3 py-2 border rounded-lg">
                </div>
                <div class="flex justify-between items-center pt-2">
                    <a href="/" class="text-sm text-slate-500 hover:underline">Cancel</a>
                    <button type="submit" class="py-2 px-4 bg-indigo-600 text-white rounded-lg">Save Changes</button>
                </div>
            </form>
        </div>
    `
)