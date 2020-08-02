package com.aarshinkov.web.storycom.utils;

import lombok.*;
import org.slf4j.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@ToString
public class PageImpl extends Page
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Override
  public Long getTotalPages()
  {

    globalTotalElements = getGlobalTotalElements();
    maxElementsPerPage = getMaxElementsPerPage();

//    LOG.debug("gte: " + globalTotalElements);
//    LOG.debug("mepp: " + maxElementsPerPage);
    if (globalTotalElements % maxElementsPerPage != 0)
    {
      return (globalTotalElements / maxElementsPerPage) + 1;
    }

    return globalTotalElements / maxElementsPerPage;
  }

  @Override
  public boolean isFirst()
  {
    return getCurrentPage() == 1;
  }

  @Override
  public boolean isLast()
  {
    return getTotalPages().equals(getCurrentPage());
  }

  @Override
  public boolean hasNext(Integer currentPage)
  {
    long totalPages = getTotalPages();

    if (totalPages == 1)
    {
      return false;
    }

    if (totalPages > 1)
    {
      return currentPage < totalPages;
    }

    return currentPage == totalPages;
  }

  @Override
  public boolean hasPrevious(Integer currentPage)
  {
    long totalPages = getTotalPages();

    if ((currentPage == totalPages) || (currentPage < totalPages && currentPage > 1))
    {
      return true;
    }

    return currentPage != 1;
  }
}
