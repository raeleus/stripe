package com.ray3k.stripe.filechooser;

import com.badlogic.gdx.files.FileHandle;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.nfd.NFDFilterItem;
import org.lwjgl.util.nfd.NFDPathSetEnum;
import org.lwjgl.util.nfd.NativeFileDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.util.nfd.NativeFileDialog.*;

public final class DesktopFileChooser implements FileChooser {

    @Override
    public void chooseFile(FileCallback callback, FileChooserConfig config) {
        Objects.requireNonNull(config);
        Objects.requireNonNull(callback);
        var defaultDirPath = config.defaultDir != null ? config.defaultDir.path() : null;

        try (MemoryStack stack = stackPush()) {
            PointerBuffer outPath = stack.mallocPointer(1);

            NFDFilterItem.Buffer filter = null;
            if (config.fileNameFilter != null) {
                filter = NFDFilterItem.malloc(config.fileNameFilter.size());
                var i = 0;
                for (String filterStr : config.fileNameFilter) {
                    var filterSplit = filterStr.split("/");
                    if (filterSplit.length == 2) {
                        filter.get(i)
                                .name(stack.UTF8(filterSplit[0]))
                                .spec(stack.UTF8(filterSplit[1]));

                        i++;
                    }
                }
            }
            // Let the os decide what default path to choose! No? Here configuration.directory.file().getPath()
            int resultValue = 1;
            switch (config.dialogType) {
                case Folder -> resultValue = NativeFileDialog.NFD_PickFolder(outPath, defaultDirPath);
                case Save ->
                        resultValue = NativeFileDialog.NFD_SaveDialog(outPath, filter, defaultDirPath, config.defaultName);
                case Open -> resultValue = NativeFileDialog.NFD_OpenDialog(outPath, filter, defaultDirPath);
                case OpenMulti -> {
                    resultValue = NativeFileDialog.NFD_OpenDialogMultiple(outPath, filter, defaultDirPath);
                    switch (resultValue) {
                        case NFD_OKAY -> {
                            List<FileHandle> fileHandleList = new ArrayList<>();
                            long pathSet = outPath.get(0);
                            NFDPathSetEnum psEnum = NFDPathSetEnum.calloc(stack);
                            NFD_PathSet_GetEnum(pathSet, psEnum);
                            while (NFD_PathSet_EnumNext(psEnum, outPath) == NFD_OKAY && outPath.get(0) != NULL) {
                                fileHandleList.add(new FileHandle(outPath.getStringUTF8(0)));
                                NFD_PathSet_FreePath(outPath.get(0));
                            }
                            NFD_PathSet_FreeEnum(psEnum);
                            NFD_PathSet_Free(pathSet);
                            callback.onFileChosen(fileHandleList);
                        }

                        case NativeFileDialog.NFD_CANCEL -> callback.onCancel();
                        case NativeFileDialog.NFD_ERROR -> callback.onError(new Exception(NFD_GetError()));
                    }
                    return;
                }

                default -> throw new IllegalArgumentException("Invalid dialog type: " + config.dialogType);
            }

            switch (resultValue) {
                case NativeFileDialog.NFD_OKAY:
                    FileHandle file = new FileHandle(outPath.getStringUTF8(0));
                    callback.onFileChosen(file);
                    NFD_FreePath(outPath.get(0));
                    break;
                case NativeFileDialog.NFD_CANCEL:
                    callback.onCancel();
                    break;
                case NativeFileDialog.NFD_ERROR:
                    callback.onError(new Exception(NFD_GetError()));
                    break;
            }

        }
    }
}
