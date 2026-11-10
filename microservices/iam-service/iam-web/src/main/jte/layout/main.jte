@param String title
@param gg.jte.Content content
@param String lang = "en"

<!DOCTYPE html>
<html lang="${lang}">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - ISTAD IAM</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 text-slate-900 min-h-screen flex flex-col justify-between">
    <header class="bg-white border-b border-slate-200 px-6 py-4 flex justify-between items-center shadow-sm">
        <a href="/" class="text-xl font-bold text-indigo-600">ISTAD Identity</a>
        <div class="flex items-center space-x-4">
            <div class="text-sm">
                <a href="?lang=en" class="px-2 py-1 rounded ${"en".equals(lang) ? "font-bold underline text-indigo-600" : "text-slate-500"}">English</a> |
                <a href="?lang=km" class="px-2 py-1 rounded ${"km".equals(lang) ? "font-bold underline text-indigo-600" : "text-slate-500"}">ខ្មែរ</a>
            </div>
        </div>
    </header>

    <main class="flex-grow flex items-center justify-center p-6">
        ${content}
    </main>

    <footer class="text-center py-4 text-xs text-slate-400 border-t border-slate-200">
        &copy; 2026 Institute of Science and Technology Advanced Development (ISTAD)
    </footer>
</body>
</html>