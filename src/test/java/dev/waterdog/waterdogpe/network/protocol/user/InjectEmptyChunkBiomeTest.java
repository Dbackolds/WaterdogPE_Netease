package dev.waterdog.waterdogpe.network.protocol.user;

import dev.waterdog.waterdogpe.network.protocol.ProtocolVersion;
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InjectEmptyChunkBiomeTest {

    @Test
    void requestModeChunkStaysEmptyWithoutNeteaseSupport() {
        LevelChunkPacket packet = chunk(false);

        assertTrue(packet.isRequestSubChunks());
        assertEquals(0, packet.getData().readableBytes());
    }

    @Test
    void requestModeChunkCarriesBiomePaletteWhenNeteaseSupportEnabled() {
        LevelChunkPacket packet = chunk(true);

        assertTrue(packet.isRequestSubChunks());
        assertTrue(packet.getData().isReadable(), "request-mode chunk must not be an empty blob");
        assertEquals(3, packet.getData().getByte(packet.getData().readerIndex()));
    }

    private static LevelChunkPacket chunk(boolean neteaseClientSupport) {
        return PlayerRewriteUtils.injectEmptyChunk(
                0, 0, PlayerRewriteUtils.DIMENSION_NETHER,
                ProtocolVersion.MINECRAFT_PE_1_21_130, true, neteaseClientSupport);
    }
}
