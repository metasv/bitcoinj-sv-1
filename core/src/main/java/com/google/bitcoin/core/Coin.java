/**
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

package com.google.bitcoin.core;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.common.math.LongMath;

/**
 * Represents a monetary Bitcoin value. This class is immutable.
 */
public final class Coin implements Comparable<Coin>, Serializable {

    public static final Coin ZERO = new Coin(BigInteger.ZERO);
    public static final Coin ONE = new Coin(BigInteger.ONE);
    public static final Coin TEN = new Coin(BigInteger.TEN);

    private final long value;

    private Coin(final long satoshis) {
        this.value = satoshis;
    }

    public Coin(final BigInteger value) {
        this.value = value.longValue();
    }

    public Coin(final String value, final int radix) {
        this(new BigInteger(value, radix));
    }

    public Coin(final byte[] value) {
        this(new BigInteger(value));
    }

    public static Coin valueOf(final long satoshis) {
        return new Coin(satoshis);
    }

    public Coin add(final Coin value) {
        return new Coin(LongMath.checkedAdd(this.value, value.value));
    }

    public Coin subtract(final Coin value) {
        return new Coin(LongMath.checkedSubtract(this.value, value.value));
    }

    public Coin multiply(final Coin value) {
        return new Coin(LongMath.checkedMultiply(this.value, value.value));
    }

    public Coin multiply(final long value) {
        return new Coin(LongMath.checkedMultiply(this.value, value));
    }

    public Coin divide(final Coin value) {
        return new Coin(this.value / value.value);
    }

    public Coin[] divideAndRemainder(final Coin value) {
        return new Coin[] { new Coin(this.value / value.value), new Coin(this.value % value.value) };
    }

    public Coin shiftLeft(final int n) {
        return new Coin(this.value << n);
    }

    public Coin shiftRight(final int n) {
        return new Coin(this.value >> n);
    }

    public int signum() {
        if (this.value == 0)
            return 0;
        return this.value < 0 ? -1 : 1;
    }

    public Coin negate() {
        return new Coin(-this.value);
    }

    public byte[] toByteArray() {
        return BigInteger.valueOf(this.value).toByteArray();
    }

    public long longValue() {
        return this.value;
    }

    public double doubleValue() {
        return this.value;
    }

    public BigInteger toBigInteger() {
        return BigInteger.valueOf(value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != getClass())
            return false;
        final Coin other = (Coin) o;
        if (this.value != other.value)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int) this.value;
    }

    @Override
    public int compareTo(final Coin other) {
        if (this.value == other.value)
            return 0;
        return this.value > other.value ? 1 : -1;
    }
}
