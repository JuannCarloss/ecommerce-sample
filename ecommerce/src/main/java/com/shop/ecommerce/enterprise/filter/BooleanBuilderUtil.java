package com.shop.ecommerce.enterprise.filter;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.shop.ecommerce.enterprise.ValidationException;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class BooleanBuilderUtil {
    public static BooleanBuilder buildPredicateFromFilter(String filter, Class<?> classes) {
        if (filter == null || filter.isEmpty()) {
            return new BooleanBuilder();
        }

        BooleanBuilder predicate = new BooleanBuilder();
        String[] parts = filter.split("\\+");

        if (parts.length == 3) {
            try {
                Field field = getFieldRecursively(classes, parts[0]);
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                PathBuilder<?> fieldPath = new PathBuilder<>(fieldType, field.getName());

                switch (parts[1].toLowerCase()) {
                    case "equal":
                        predicate.and(fieldPath.eq(Expressions.constant(parts[2])));
                        break;
                    case "notEqual":
                        predicate.and(fieldPath.ne(Expressions.constant(parts[2])));
                        break;
                    case "greater":
                        predicate.and(Expressions.booleanTemplate("{0} > {1}", fieldPath, getType(fieldType, parts[2])));
                        break;
                    case "lesser":
                        predicate.and(Expressions.booleanTemplate("{0} < {1}", fieldPath, getType(fieldType, parts[2])));
                        break;
                    case "greaterequal":
                        predicate.and(Expressions.booleanTemplate("{0} >= {1}", fieldPath, getType(fieldType, parts[2])));
                        break;
                    case "lesserequal":
                        predicate.and(Expressions.booleanTemplate("{0} <= {1}", fieldPath, getType(fieldType, parts[2])));
                        break;
                    case "like":
                        predicate.and(Expressions.booleanTemplate("{0} like '%'||{1}||'%'", fieldPath, Expressions.constant(parts[2])));
                        break;
                    default:
                        throw new ValidationException("Operator not found");
                }
            } catch (NoSuchFieldException e) {
                throw new ValidationException("Field not Found");
            } catch (Exception e) {
                throw new ValidationException("Illegal access in Field");
            }
        }

        if (parts.length == 4) {
            try {
                Field field = getFieldRecursively(classes, parts[0]);
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                PathBuilder<?> fieldPath = new PathBuilder<>(fieldType, field.getName());

                switch (parts[1].toLowerCase()) {
                    case "between":
                        predicate.and(Expressions.booleanTemplate("{0} >= {1} AND {0} <= {2}", fieldPath, getType(fieldType, parts[2]), getType(fieldType, parts[3])));
                        break;
                    default:
                        throw new ValidationException("Operator not supported");
                }
            } catch (NoSuchFieldException e) {
                throw new ValidationException("Field not Found");
            } catch (Exception e) {
                throw new ValidationException("Illegal access in Field");
            }
        }

        return predicate;
    }

    public static Expression getType(Class<?> fieldType, String part) {
        if (fieldType == Integer.class || fieldType == int.class) {
            return Expressions.constant(Integer.parseInt(part));
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Expressions.constant(Double.parseDouble(part));
        } else if (fieldType == LocalDate.class) {
            return Expressions.constant(LocalDate.parse(part));
        }
        return Expressions.constant(part);
    }

    private static Field getFieldRecursively(Class<?> classes, String fieldName) throws NoSuchFieldException {
        try {
            return classes.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (classes.getSuperclass() != null) {
                return getFieldRecursively(classes.getSuperclass(), fieldName);
            } else {
                throw e;
            }
        }
    }
}
