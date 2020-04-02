package com.kyriexu.service;

import com.kyriexu.mapper.NationMapper;
import com.kyriexu.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationService {
    @Autowired
    private NationMapper nationMapper;

    /**
     * 获取所有的籍贯
     * @return 籍贯列表
     */
    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
