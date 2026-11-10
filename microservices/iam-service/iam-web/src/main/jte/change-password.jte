@param String error = null
@param String lang = "en"
@param java.util.function.Function<String, String> msg

@template.layout.main(
    title = "Change Password",
    lang = lang,
    content = @`
        <div class="w-full max-w-md bg-white rounded-xl shadow-md border border-slate-200 p-8">
            <h1 class="text-xl font-bold text-slate-800 mb-6">Change Password</h1>
            @if(error != null)
                <div class="mb-4 p-3 bg-red-50 text-red-600 rounded text-sm">${error}</div>
            @endif
            <form method="post" action="/profile/password" class="space-y-4">
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Current Password</label>
                    <input type="password" name="currentPassword" required class="w-full px-3 py-2 border rounded-lg">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">New Password</label>
                    <input type="password" name="newPassword" required class="w-full px-3 py-2 border rounded-lg">
                </div>
                <div>
                    <label class="block text-sm font-medium text-slate-700 mb-1">Confirm New Password</label>
                    <input type="password" name="confirmPassword" required class="w-full px-3 py-2 border rounded-lg">
                </div>
                <div class="flex justify-between items-center pt-2">
                    <a href="/" class="text-sm text-slate-500 hover:underline">Cancel</a>
                    <button type="submit" class="py-2 px-4 bg-indigo-600 text-white rounded-lg">Update Password</button>
                </div>
            </form>
        </div>
    `
)