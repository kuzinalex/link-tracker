package ru.tinkoff.edu.java.scrapper.entity.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "link")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_id_seq")
	@SequenceGenerator(name="link_id_seq",sequenceName="link_id_seq", allocationSize=1)
	private Long id;
	private String url;
	@Column(name = "check_time")
	private OffsetDateTime checkTime;
	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "linkId", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SubscriptionEntity> subscription;
}
