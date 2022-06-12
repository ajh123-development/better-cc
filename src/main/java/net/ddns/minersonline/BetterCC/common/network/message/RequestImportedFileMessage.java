/* SPDX-License-Identifier: MIT */

package net.ddns.minersonline.BetterCC.common.network.message;

import net.ddns.minersonline.BetterCC.client.gui.FileChooserScreen;
import net.ddns.minersonline.BetterCC.common.bus.device.rpc.item.FileImportExportCardItemDevice;
import net.ddns.minersonline.BetterCC.common.network.Network;
import net.ddns.minersonline.BetterCC.common.util.TranslationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class RequestImportedFileMessage extends AbstractMessage {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TranslatableComponent FILE_TOO_LARGE_TEXT = TranslationUtils.text("message.{mod}.import_file.file_too_large");

    ///////////////////////////////////////////////////////////////////

    private int id;

    ///////////////////////////////////////////////////////////////////

    public RequestImportedFileMessage(final int id) {
        this.id = id;
    }

    public RequestImportedFileMessage(final FriendlyByteBuf buffer) {
        super(buffer);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    public void fromBytes(final FriendlyByteBuf buffer) {
        id = buffer.readVarInt();
    }

    @Override
    public void toBytes(final FriendlyByteBuf buffer) {
        buffer.writeVarInt(id);
    }

    ///////////////////////////////////////////////////////////////////

    @Override
    protected void handleMessage(final NetworkEvent.Context context) {
        FileChooserScreen.openFileChooserForLoad(new FileChooserScreen.FileChooserCallback() {
            @Override
            public void onFileSelected(final Path path) {
                try {
                    final String fileName = path.getFileName().toString();
                    final byte[] data = Files.readAllBytes(path);
                    if (data.length > FileImportExportCardItemDevice.MAX_TRANSFERRED_FILE_SIZE) {
                        Network.sendToServer(new ClientCanceledImportFileMessage(id));
                        Minecraft.getInstance().gui.getChat().addMessage(FILE_TOO_LARGE_TEXT
                            .withStyle(s -> s.withColor(TextColor.fromRgb(0xFFA0A0))));
                    } else {
                        Network.sendToServer(new ImportedFileMessage(id, fileName, data));
                    }
                } catch (final IOException e) {
                    LOGGER.error(e);
                }
            }

            @Override
            public void onCanceled() {
                Network.sendToServer(new ClientCanceledImportFileMessage(id));
            }
        });
    }
}
