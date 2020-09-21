package br.com.buy.domain.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType.class)
@Entity
@Table(name = "food")
public class Food extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID uuid;

  @Type(type = "jsonb")
  @Column(columnDefinition = "metadata")
  private JsonNode attributes;

  @Column(name = "base_qty")
  private int baseQuantity;

  @Column(name = "base_unit")
  private String baseUnit;

  @Column(name = "category_id")
  private int categoryId;

  @Column
  private String description;

  @Column(name = "rae")
  private String retinolActivityEquivalent;

  @Column(name = "re")
  private String retinolEquivalent;

  @Column(name = "vitamin_c")
  private String vitamin;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cart_uuid", referencedColumnName = "uuid",
      foreignKey = @ForeignKey(name = "uuid"))
  private Cart cart;

}
