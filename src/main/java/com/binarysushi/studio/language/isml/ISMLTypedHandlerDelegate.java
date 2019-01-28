package com.binarysushi.studio.language.isml;

import com.intellij.codeInsight.CodeInsightSettings;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class ISMLTypedHandlerDelegate extends TypedHandlerDelegate {
    @NotNull
    @Override
    public Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        if (!CodeInsightSettings.getInstance().AUTOINSERT_PAIR_BRACKET) return Result.CONTINUE;

        if (c != '{') return Result.CONTINUE;
        if (!shouldProcess(file)) return Result.CONTINUE;

        int offset = editor.getCaretModel().getOffset();
        if (shouldCloseBrace(editor, offset, c)) {
            editor.getDocument().insertString(offset, "}");
            return Result.STOP;
        }
        return Result.CONTINUE;
    }

    private static boolean shouldCloseBrace(Editor editor, int offset, char c) {
        CharSequence text = editor.getDocument().getCharsSequence();

        if (offset < 2) return false;
        if (c != '{' || text.charAt(offset - 2) != '$') return false;

        if (offset < text.length()) {
            char next = text.charAt(offset);
            if (next == '}') return false;
            if (Character.isLetterOrDigit(next)) return false;
        }

        return true;
    }

    static boolean shouldProcess(PsiFile file) {
        return ISMLFileType.INSTANCE.equals(file.getViewProvider().getFileType());
    }
}
