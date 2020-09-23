package br.com.buy.domain.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@TypeDef(name = "jsonb", typeClass = JsonNodeBinaryType.class)
@Getter
@Setter
@Entity
@Table(name = "food")
public class Food extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID uuid;

  @Column(name = "external_id", unique = true)
  private int externalId;

  @Column(name = "base_qty")
  private int baseQuantity;

  @Column(name = "base_unit")
  private String baseUnit;

  @Column(name = "category_id")
  private int categoryId;

  @Column
  private String name;

  private int count;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(name = "food_cart")
  private List<Cart> cart;

}
