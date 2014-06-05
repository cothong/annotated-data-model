/******************************************************************************
 ** This data and information is proprietary to, and a valuable trade secret
 ** of, Basis Technology Corp.  It is given in confidence by Basis Technology
 ** and may only be used as permitted under the license agreement under which
 ** it has been distributed, and in no other way.
 **
 ** Copyright (c) 2014 Basis Technology Corporation All rights reserved.
 **
 ** The technical data and information provided herein are provided with
 ** `limited rights', and the computer software provided herein is provided
 ** with `restricted rights' as those terms are defined in DAR and ASPR
 ** 7-104.9(a).
 ******************************************************************************/

package com.basistech.rosette.dm;

import com.basistech.util.ISO15924;
import com.basistech.util.LanguageCode;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * The results of running language detection on a region of text.
 * Typically, there will be multiple of these since detectors
 * return multiple possible answers.
 */
public class LanguageDetection extends Attribute {

    public static class DetectionResult {
        private final LanguageCode language;
        private final String encoding;
        private final ISO15924 script;
        private final double confidence;

        protected DetectionResult() {
            language = LanguageCode.UNKNOWN;
            encoding = null;
            script = ISO15924.Zyyy;
            confidence = 0.0;
        }

        public DetectionResult(LanguageCode language,
                               String encoding,
                               ISO15924 script,
                               double confidence) {
            this.language = language;
            this.encoding = encoding;
            this.script = script;
            this.confidence = confidence;
        }

        public LanguageCode getLanguage() {
            return language;
        }

        public String getEncoding() {
            return encoding;
        }

        public ISO15924 getScript() {
            return script;
        }

        public double getConfidence() {
            return confidence;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            DetectionResult that = (DetectionResult) o;

            if (Double.compare(that.confidence, confidence) != 0) {
                return false;
            }
            if (encoding != null ? !encoding.equals(that.encoding) : that.encoding != null) {
                return false;
            }
            if (language != that.language) {
                return false;
            }
            if (script != that.script) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = language.hashCode();
            result = 31 * result + (encoding != null ? encoding.hashCode() : 0);
            result = 31 * result + (script != null ? script.hashCode() : 0);
            temp = Double.doubleToLongBits(confidence);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("language", language)
                    .add("encoding", encoding)
                    .add("script", script)
                    .add("confidence", confidence)
                    .toString();
        }
    }

    private final List<DetectionResult> detectionResults;

    LanguageDetection() {
        // make Jackson happy
        super();
        this.detectionResults = Lists.newArrayList();
    }

    LanguageDetection(int startOffset, int endOffset, List<DetectionResult> detectionResults, Map<String, Object> extendedProperties) {
        super(startOffset, endOffset, extendedProperties);
        this.detectionResults = detectionResults;
    }

    LanguageDetection(int startOffset,
                      int endOffset,
                      List<DetectionResult> detectionResults) {
        super(startOffset, endOffset);
        this.detectionResults = detectionResults;
    }

    public List<DetectionResult> getDetectionResults() {
        return detectionResults;
    }

    @Override
    protected Objects.ToStringHelper toStringHelper() {
        return Objects.toStringHelper(this)
                .add("detectionResults", detectionResults);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        LanguageDetection that = (LanguageDetection) o;

        if (!detectionResults.equals(that.detectionResults)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + detectionResults.hashCode();
        return result;
    }

    /**
     * A builder for language detection results.
     */
    public static class Builder extends Attribute.Builder {
        private List<DetectionResult> detectionResults;

        public Builder(int startOffset, int endOffset, List<DetectionResult> detectionResults) {
            super(startOffset, endOffset);
            this.detectionResults = detectionResults;
        }

        public Builder(LanguageDetection toCopy) {
            super(toCopy);
            this.detectionResults = toCopy.detectionResults;
        }

        public LanguageDetection build() {
            return new LanguageDetection(startOffset, endOffset, detectionResults, extendedProperties);
        }
    }
}