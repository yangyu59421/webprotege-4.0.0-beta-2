package edu.stanford.bmir.protege.web.shared.frame;

import com.google.auto.value.AutoValue;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import edu.stanford.bmir.protege.web.shared.entity.OWLAnnotationPropertyData;
import edu.stanford.bmir.protege.web.shared.entity.OWLEntityData;
import org.semanticweb.owlapi.model.OWLEntity;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 28/11/2012
 */
@AutoValue
@GwtCompatible(serializable = true)
public abstract class AnnotationPropertyFrame implements EntityFrame<OWLAnnotationPropertyData>, HasPropertyValueList {

    @Nonnull
    public abstract OWLAnnotationPropertyData getSubject();

    @Nonnull
    public abstract ImmutableSet<PropertyAnnotationValue> getPropertyValues();

    @Nonnull
    public abstract ImmutableSet<OWLEntityData> getDomains();

    @Nonnull
    public abstract ImmutableSet<OWLEntityData> getRanges();

    @Nonnull
    public static AnnotationPropertyFrame get(@Nonnull OWLAnnotationPropertyData subject,
                                              @Nonnull ImmutableSet<PropertyAnnotationValue> propertyValues,
                                              @Nonnull ImmutableSet<OWLEntityData> domains,
                                              @Nonnull ImmutableSet<OWLEntityData> ranges) {
        return new AutoValue_AnnotationPropertyFrame(subject,
                                                     propertyValues,
                                                     domains,
                                                     ranges);
    }

    @Override
    public PropertyValueList getPropertyValueList() {
        return new PropertyValueList(getPropertyValues());
    }
}
