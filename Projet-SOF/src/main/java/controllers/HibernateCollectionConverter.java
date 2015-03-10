package controllers;


import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.Mapper;

import org.hibernate.collection.internal.PersistentBag;

class HibernateCollectionConverter extends CollectionConverter {
    HibernateCollectionConverter(Mapper mapper) {
        super(mapper);
    }

    public boolean canConvert(Class type) {
        return super.canConvert(type) || type == PersistentBag.class ; 
    }
}
