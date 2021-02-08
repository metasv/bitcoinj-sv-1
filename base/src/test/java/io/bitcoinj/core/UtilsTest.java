/*
 * Copyright 2011 Thilo Planz
 * Copyright 2014 Andreas Schildbach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.bitcoinj.core;

import com.google.common.io.BaseEncoding;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @Test
    public void testReverseBytes() {
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5}, Utils.reverseBytes(new byte[]{5, 4, 3, 2, 1}));
    }

    @Test
    public void testReverseDwordBytes() {
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 8}, Utils.reverseDwordBytes(new byte[]{4, 3, 2, 1, 8, 7, 6, 5}, -1));
        assertArrayEquals(new byte[]{1, 2, 3, 4}, Utils.reverseDwordBytes(new byte[]{4, 3, 2, 1, 8, 7, 6, 5}, 4));
        assertArrayEquals(new byte[0], Utils.reverseDwordBytes(new byte[]{4, 3, 2, 1, 8, 7, 6, 5}, 0));
        assertArrayEquals(new byte[0], Utils.reverseDwordBytes(new byte[0], 0));
    }

    @Test
    public void testMaxOfMostFreq() {
        assertEquals(0, Utils.maxOfMostFreq());
        assertEquals(0, Utils.maxOfMostFreq(0, 0, 1));
        assertEquals(2, Utils.maxOfMostFreq(1, 1, 2, 2));
        assertEquals(1, Utils.maxOfMostFreq(1, 1, 2, 2, 1));
        assertEquals(-1, Utils.maxOfMostFreq(-1, -1, 2, 2, -1));
    }

    @Test
    public void compactEncoding() {
        assertEquals(new BigInteger("1234560000", 16), Utils.decodeCompactBits(0x05123456L));
        assertEquals(new BigInteger("c0de000000", 16), Utils.decodeCompactBits(0x0600c0de));
        assertEquals(0x05123456L, Utils.encodeCompactBits(new BigInteger("1234560000", 16)));
        assertEquals(0x0600c0deL, Utils.encodeCompactBits(new BigInteger("c0de000000", 16)));
    }

    @Test
    public void dateTimeFormat() {
        assertEquals("2014-11-16T10:54:33Z", Utils.dateTimeFormat(1416135273781L));
        assertEquals("2014-11-16T10:54:33Z", Utils.dateTimeFormat(new Date(1416135273781L)));
    }

    @Test
    public void testReadUint16BE() {
        assertEquals(Utils.readUint16BE(BaseEncoding.base16().decode("0000"), 0), 0L);
        assertEquals(Utils.readUint16BE(BaseEncoding.base16().decode("00FF"), 0), (long) Math.pow(2, 8) - 1);
        assertEquals(Utils.readUint16BE(BaseEncoding.base16().decode("FFFF"), 0), (long) Math.pow(2, 16) - 1);
    }

    @Test
    public void testReadUint32BE() {
        assertEquals(Utils.readUint32BE(BaseEncoding.base16().decode("00000000"), 0), 0L);
        assertEquals(Utils.readUint32BE(BaseEncoding.base16().decode("000000FF"), 0), (long) Math.pow(2, 8) - 1);
        assertEquals(Utils.readUint32BE(BaseEncoding.base16().decode("0000FFFF"), 0), (long) Math.pow(2, 16) - 1);
        assertEquals(Utils.readUint32BE(BaseEncoding.base16().decode("00FFFFFF"), 0), (long) Math.pow(2, 24) - 1);
        assertEquals(Utils.readUint32BE(BaseEncoding.base16().decode("FFFFFFFF"), 0), (long) Math.pow(2, 32) - 1);
    }

    @Test
    public void testReadUint32() {
        assertEquals(Utils.readUint32(BaseEncoding.base16().decode("00000000"), 0), 0L);
        assertEquals(Utils.readUint32(BaseEncoding.base16().decode("FF000000"), 0), (long) Math.pow(2, 8) - 1);
        assertEquals(Utils.readUint32(BaseEncoding.base16().decode("FFFF0000"), 0), (long) Math.pow(2, 16) - 1);
        assertEquals(Utils.readUint32(BaseEncoding.base16().decode("FFFFFF00"), 0), (long) Math.pow(2, 24) - 1);
        assertEquals(Utils.readUint32(BaseEncoding.base16().decode("FFFFFFFF"), 0), (long) Math.pow(2, 32) - 1);
    }

    @Test
    public void testReadInt64() {
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("0000000000000000"), 0), 0L);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FF00000000000000"), 0), (long) Math.pow(2, 8) - 1);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FFFF000000000000"), 0), (long) Math.pow(2, 16) - 1);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FFFFFF0000000000"), 0), (long) Math.pow(2, 24) - 1);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FFFFFFFF00000000"), 0), (long) Math.pow(2, 32) - 1);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FFFFFFFFFF000000"), 0), (long) Math.pow(2, 40) - 1);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FFFFFFFFFFFF0000"), 0), (long) Math.pow(2, 48) - 1);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FFFFFFFFFFFFFF00"), 0), (long) Math.pow(2, 56) - 1);
        assertEquals(Utils.readInt64(BaseEncoding.base16().decode("FFFFFFFFFFFFFFFF"), 0), -1L);
    }
}
